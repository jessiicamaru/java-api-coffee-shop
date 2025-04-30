package com.example.coffeeshop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikesDto {
    private String coffeeId;
    private String coffeeTitle;
    private String coffeePhotoUrl;
    private Double coffeeCost;
    private String coffeeDescription;
    private String categoryTitle;
    private String categoryId;
}
