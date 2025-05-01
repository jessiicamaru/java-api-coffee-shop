package com.example.coffeeshop.service;

import com.example.coffeeshop.dto.PromotionDto;

import java.util.List;

public interface PromotionService {
    List<PromotionDto> getApplicablePromotions(String uid);
}