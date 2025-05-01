package com.example.coffeeshop.dto;

import com.example.coffeeshop.constants.DiscountTarget;
import com.example.coffeeshop.constants.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDiscountDto {
    private Long id;
    private DiscountTarget discountTarget;
    private DiscountType discountType;
    private Double discountValue;
}