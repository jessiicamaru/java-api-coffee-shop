package com.example.coffeeshop.service;

import com.example.coffeeshop.dto.OrderRequest;

public interface OrderService {
    void createOrder(OrderRequest orderRequest);
}
