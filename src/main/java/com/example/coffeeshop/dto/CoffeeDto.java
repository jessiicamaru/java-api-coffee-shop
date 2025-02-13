package com.example.coffeeshop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CoffeeDto {
    private String coffeeId;
    private String coffeeTitle;
    private String coffeePhotoUrl;
    private Double coffeeCost;
    private String coffeeDescription;
    private String categoryTitle;
    private String categoryId;
}

