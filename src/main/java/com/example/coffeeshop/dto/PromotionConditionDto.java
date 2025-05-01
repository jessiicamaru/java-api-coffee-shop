package com.example.coffeeshop.dto;

import com.example.coffeeshop.constants.ConditionType;
import com.example.coffeeshop.constants.Operator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionConditionDto {
    private Long id;
    private ConditionType conditionType;
    private Operator operator;
    private Double conditionValue;
}

