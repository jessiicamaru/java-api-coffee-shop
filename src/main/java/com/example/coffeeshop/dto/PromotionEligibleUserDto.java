package com.example.coffeeshop.dto;

import com.example.coffeeshop.constants.CriteriaType;
import com.example.coffeeshop.constants.Operator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionEligibleUserDto {
    private Long id;
    private CriteriaType criteriaType;
    private Operator operator;
    private String criteriaValue;
}
