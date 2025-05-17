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
    private Double originalTotal;
    private Double originalFee;
    private Double longitude;
    private Double latitude;
    private String note;
    private String receiveCustomer;
    private List<CoffeeOrder> coffees;
    private List<PromotionRequest> promotion;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoffeeOrder {
        private String coffeeId;
        private String size;
        private int quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionRequest {
        private String promotionId;
        private String promotionCode;
    }
}
