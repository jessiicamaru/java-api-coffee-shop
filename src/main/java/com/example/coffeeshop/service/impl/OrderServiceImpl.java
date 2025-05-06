package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.dto.OrderRequest;
import com.example.coffeeshop.dto.OrderResponse;
import com.example.coffeeshop.model.*;
import com.example.coffeeshop.repository.*;
import com.example.coffeeshop.service.OrderService;
import com.example.coffeeshop.socket.CoffeeWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private OrderCoffeeRepository orderCoffeeRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionUsageRepository promotionUsageRepository;

    @Autowired
    private OrderPromotionRepository orderPromotionRepository;

    @Override
    public void createOrder(OrderRequest orderRequest) {
        // 1. Lấy thông tin từ OrderRequest
        String orderId = orderRequest.getOrderId();
        String uid = orderRequest.getUid();
        String address = orderRequest.getAddress();
        Double total = orderRequest.getTotal();
        Double fee = orderRequest.getFee();
        Double originalTotal = orderRequest.getOriginalTotal();
        Double originalFee = orderRequest.getOriginalFee();
        Double longitude = orderRequest.getLongitude();
        Double latitude = orderRequest.getLatitude();
        String note = orderRequest.getNote();
        User user = userRepository.findByUid(uid);

        // 3. Kiểm tra tính hợp lệ của các khuyến mãi
        List<OrderRequest.PromotionRequest> promotions = orderRequest.getPromotion() != null ? orderRequest.getPromotion() : List.of();
        for (OrderRequest.PromotionRequest promo : promotions) {
            String promotionId = promo.getPromotionId();
            com.example.coffeeshop.model.Promotion promotion = promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid promotion ID: " + promotionId));

            // Kiểm tra thời gian hoạt động
            if (LocalDateTime.now().isBefore(promotion.getStartDate()) ||
                    LocalDateTime.now().isAfter(promotion.getEndDate())) {
                throw new IllegalArgumentException("Promotion " + promotionId + " is not active");
            }

            // Kiểm tra giới hạn sử dụng
            int usageCount = promotionUsageRepository.countUsageByPromotionAndUser(promotionId, uid);
            if (promotion.getUsageLimitPerUser() > 0 && usageCount >= promotion.getUsageLimitPerUser()) {
                throw new IllegalArgumentException("Promotion " + promotionId + " usage limit exceeded");
            }
        }

        // 4. Tạo và lưu UserOrder
        UserOrder userOrder = new UserOrder(orderId, user, address, 0, total, fee, originalTotal, originalFee, longitude, latitude, note);
        userOrderRepository.save(userOrder);
        // 6. Lưu chi tiết đơn hàng vào order_coffee
        List<OrderCoffee> coffeeOrders = orderRequest.getCoffees().stream().map(coffeeItem -> {
            Coffee coffee = coffeeRepository.findByCoffeeId(coffeeItem.getCoffeeId());

            return OrderCoffee.builder()
                    .userOrder(userOrder)
                    .coffee(coffee)
                    .quantity(coffeeItem.getQuantity())
                    .size(coffeeItem.getSize())
                    .build();
        }).toList();
        orderCoffeeRepository.saveAll(coffeeOrders);

        // 5. Lưu thông tin sử dụng khuyến mãi
        for (OrderRequest.PromotionRequest promo : promotions) {
            String promotionId = promo.getPromotionId();

            // Lưu vào promotion_usage
            PromotionUsage usage = new PromotionUsage();
            usage.setUid(uid);
            usage.setOrderId(orderId);
            usage.setPromotionId(promotionId);
            usage.setUsedAt(LocalDateTime.now());
            promotionUsageRepository.save(usage);

            // Lưu vào order_promotions
            OrderPromotion orderPromotion = new OrderPromotion();
            orderPromotion.setOrderId(orderId);
            orderPromotion.setPromotionId(promotionId);
            orderPromotionRepository.save(orderPromotion);
        }

        try {
            CoffeeWebSocketHandler.sendOrderStatus(uid, orderId, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Object[]> results = orderRepository.findAllOrders();

        if (results.isEmpty()) {
            return List.of();
        }

        Map<String, OrderResponse> orderMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        for (Object[] row : results) {
            String orderId = (String) row[0];
            String userName = (String) row[1];
            String email = (String) row[2];
            String address = (String) row[9];
            String uid = (String) row[16];
            LocalDateTime createdAt = (LocalDateTime) row[17];
            int stat = (int) row[10];
            Double total = (Double) row[11];
            Double fee = (Double) row[12];
            Double longitude = (Double) row[13];
            Double latitude = (Double) row[14];
            String note = (String) row[15];
            String formattedCreatedAt = createdAt.format(formatter);
            OrderResponse.CoffeeOrderInfo coffee = new OrderResponse.CoffeeOrderInfo(
                    (String) row[3],  // coffeeTitle
                    (String) row[4],  // coffeePhotoUrl
                    (Double) row[5],  // coffeeCost
                    (String) row[6],  // categoryTitle
                    ((Number) row[7]).intValue(), // quantity
                    (String) row[8]   // size
            );

            if (!orderMap.containsKey(orderId)) {
                orderMap.put(orderId, new OrderResponse(
                        orderId, // Thêm orderId vào DTO
                        new OrderResponse.UserInfo(userName, email, uid),
                        formattedCreatedAt,
                        address,
                        stat,
                        total,
                        fee,
                        longitude,
                        latitude,
                        note,
                        new ArrayList<>()
                ));
            }

            orderMap.get(orderId).getCoffees().add(coffee);
        }

        return new ArrayList<>(orderMap.values());

    }

    @Override
    public List<OrderResponse> getAllOrdersByUid(String uid) {
        List<Object[]> results = orderRepository.findAllOrdersByUid(uid);

        if (results.isEmpty()) {
            return List.of();
        }

        Map<String, OrderResponse> orderMap = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        for (Object[] row : results) {
            String orderId = (String) row[0];
            String userName = (String) row[1];
            String email = (String) row[2];
            String address = (String) row[9];
            LocalDateTime createdAt = (LocalDateTime) row[16];
            int stat = (int) row[10];
            Double total = (Double) row[11];
            Double fee = (Double) row[12];
            Double longitude = (Double) row[13];
            Double latitude = (Double) row[14];
            String note = (String) row[15];
            String formattedCreatedAt = createdAt.format(formatter);
            OrderResponse.CoffeeOrderInfo coffee = new OrderResponse.CoffeeOrderInfo(
                    (String) row[3],  // coffeeTitle
                    (String) row[4],  // coffeePhotoUrl
                    (Double) row[5],  // coffeeCost
                    (String) row[6],  // categoryTitle
                    ((Number) row[7]).intValue(), // quantity
                    (String) row[8]   // size
            );

            if (!orderMap.containsKey(orderId)) {
                orderMap.put(orderId, new OrderResponse(
                        orderId, // Thêm orderId vào DTO
                        new OrderResponse.UserInfo(userName, email, uid),
                        formattedCreatedAt,
                        address,
                        stat,
                        total,
                        fee,
                        longitude,
                        latitude,
                        note,
                        new ArrayList<>()
                ));
            }

            orderMap.get(orderId).getCoffees().add(coffee);
        }

        return new ArrayList<>(orderMap.values());

    }

    @Override
    public void updateStat(String uid, String orderId, int stat) {
        orderRepository.updateStat(uid, orderId, stat);
    }
}
