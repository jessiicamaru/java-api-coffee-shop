package com.example.coffeeshop.model;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class OrderStatusUpdate {
    private String userId;
    private String orderId;
    private int status;
}