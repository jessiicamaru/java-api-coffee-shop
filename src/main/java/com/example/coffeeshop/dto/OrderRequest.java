package com.example.coffeeshop.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String uid;
    private String orderId;
    private String address;
    private Double total;
    private Double fee;
    private Double longitude;
    private Double latitude;
    private String note;
    private List<CoffeeOrder> coffees;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoffeeOrder {
        private String coffeeId;
        private String size;
        private int quantity;
    }
}
