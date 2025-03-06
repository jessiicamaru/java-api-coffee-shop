package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.OrderRequest;
import com.example.coffeeshop.dto.OrderResponse;
import com.example.coffeeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return ResponseEntity.ok("Order created successfully!");
    }

    @GetMapping("/web/orders")
    public ResponseEntity<List<OrderResponse>> getOrder() {
//        model.addAttribute("orders", orderService.getAllOrders());
        List<OrderResponse> responseEntity = orderService.getAllOrders();
        return ResponseEntity.ok(responseEntity);
    }
}
