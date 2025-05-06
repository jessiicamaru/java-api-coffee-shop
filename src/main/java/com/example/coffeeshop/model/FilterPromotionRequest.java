package com.example.coffeeshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterPromotionRequest {
    private String uid;
    private Double totalAmount;
    private Double distance;
    private Integer itemCount;
}
