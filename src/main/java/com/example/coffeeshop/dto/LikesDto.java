package com.example.coffeeshop.dto;

import com.example.coffeeshop.model.Coffee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
