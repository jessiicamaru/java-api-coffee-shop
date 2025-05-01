package com.example.coffeeshop.dto;

import com.example.coffeeshop.constants.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDto {
    private String promotionId;
    private String code;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PromotionStatus status;
    private PromotionType promotionType;
    private int usageLimitPerUser;
    private List<PromotionConditionDto> conditions;
    private List<PromotionDiscountDto> discounts;
    private List<PromotionEligibleUserDto> eligibleUsers;
}




