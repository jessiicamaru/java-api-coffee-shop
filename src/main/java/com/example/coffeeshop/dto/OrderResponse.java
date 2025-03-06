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
    private List<CoffeeOrderInfo> coffees;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserInfo {
        private String name;
        private String email;
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
