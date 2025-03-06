package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.dto.OrderRequest;
import com.example.coffeeshop.dto.OrderResponse;
import com.example.coffeeshop.model.Coffee;
import com.example.coffeeshop.model.OrderCoffee;
import com.example.coffeeshop.model.User;
import com.example.coffeeshop.model.UserOrder;
import com.example.coffeeshop.repository.*;
import com.example.coffeeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void createOrder(OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();

        User user = userRepository.findByUid(orderRequest.getUid());

        UserOrder userOrder = new UserOrder(orderId, user);
        userOrderRepository.save(userOrder);

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
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Object[]> results = orderRepository.findAllOrders();

        if (results.isEmpty()) {
            return List.of();
        }

        Map<String, OrderResponse> orderMap = new LinkedHashMap<>();

        for (Object[] row : results) {
            String orderId = (String) row[0];
            String userName = (String) row[1];
            String email = (String) row[2];

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
                        new OrderResponse.UserInfo(userName, email),
                        new ArrayList<>()
                ));
            }

            orderMap.get(orderId).getCoffees().add(coffee);
        }

        return new ArrayList<>(orderMap.values());

    }


}
