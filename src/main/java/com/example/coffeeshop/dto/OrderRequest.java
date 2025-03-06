package com.example.coffeeshop.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String uid;
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
