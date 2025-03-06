package com.example.coffeeshop.service;

import com.example.coffeeshop.dto.OrderRequest;
import com.example.coffeeshop.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    void createOrder(OrderRequest orderRequest);
    List<OrderResponse> getAllOrders();
}
