package com.example.coffeeshop.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private UserInfo user;
    private String createdAt;
    private String address;
    private int stat;
    private Double total;
    private Double fee;
    private Double originalTotal;
    private Double originalFee;
    private Double longitude;
    private Double latitude;
    private String note;
    private String receiveCustomer;
    private List<CoffeeOrderInfo> coffees;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserInfo {
        private String name;
        private String email;
        private String uid;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CoffeeOrderInfo {
        private String coffeeTitle;
        private String coffeePhotoUrl;
        private Double coffeeCost;
        private String categoryTitle;
        private Integer quantity;
        private String size;
    }

}
