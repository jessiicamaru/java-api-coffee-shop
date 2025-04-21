package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.CategoryDto;
import com.example.coffeeshop.dto.OrderRequest;
import com.example.coffeeshop.dto.OrderResponse;
import com.example.coffeeshop.model.OrderStatusUpdate;
import com.example.coffeeshop.service.CategoryService;
import com.example.coffeeshop.service.OrderService;
import com.example.coffeeshop.socket.CoffeeWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService, CategoryService categoryService) {
        this.orderService = orderService;
        this.categoryService = categoryService;
    }


    @PostMapping("/create-order")
    public ResponseEntity<Integer> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return ResponseEntity.ok(1);
    }

    @GetMapping("/get-pending-orders")
    public ResponseEntity<List<OrderResponse>> getPendingOrder(@RequestParam("uid") String uid) {
        return ResponseEntity.ok(orderService.getAllOrdersByUid(uid));
    }

    @GetMapping("/web/orders")
    public String getOrder(Model model) {
        List<OrderResponse> orders = orderService.getAllOrders();
        List<CategoryDto> categories = categoryService.getAllCategories();
        Map<Integer, List<OrderResponse>> ordersByStatus = orders.stream()
                .collect(Collectors.groupingBy(OrderResponse::getStat));

        model.addAttribute("pendingOrders", ordersByStatus.getOrDefault(0, List.of()));
        model.addAttribute("preparingOrders", ordersByStatus.getOrDefault(1, List.of()));
        model.addAttribute("deliveringOrders", ordersByStatus.getOrDefault(2, List.of()));
        model.addAttribute("completedOrders", ordersByStatus.getOrDefault(3, List.of()));
        model.addAttribute("canceledOrders", ordersByStatus.getOrDefault(4, List.of()));
        model.addAttribute("categories", categories);
        return "order";
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrderJSON(Model model) {
//        model.addAttribute("orders", orderService.getAllOrders());
        List<OrderResponse> responseEntity = orderService.getAllOrders();

        return ResponseEntity.ok(responseEntity);
    }


    @PostMapping("/update-order-status")
    public ResponseEntity<String> updateOrderStatus(@RequestBody OrderStatusUpdate update) {
        try {
            String uid = update.getUserId();
            String orderId = update.getOrderId();
            int stat = update.getStatus();
            CoffeeWebSocketHandler.sendOrderStatus(
                    uid,
                    orderId,
                    stat
            );

            orderService.updateStat(uid, orderId, stat);
            return ResponseEntity.ok("Order status updated successfully");
        } catch (IOException e) {
            logger.error("Failed to send order status update for userId: {}, orderId: {}, status: {}",
                    update.getUserId(), update.getOrderId(), update.getStatus(), e);
            return ResponseEntity.status(500).body("Failed to update order status: " + e.getMessage());
        }
    }


}
