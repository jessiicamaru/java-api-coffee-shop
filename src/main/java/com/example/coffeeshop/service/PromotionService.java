package com.example.coffeeshop.service;

import com.example.coffeeshop.dto.PromotionDto;
import com.example.coffeeshop.model.FilterPromotionRequest;

import java.util.List;

public interface PromotionService {
    List<PromotionDto> findAll();
    List<String> filterPromotions(FilterPromotionRequest filterPromotionRequest);
}