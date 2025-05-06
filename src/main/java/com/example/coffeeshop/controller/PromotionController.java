package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.PromotionDto;
import com.example.coffeeshop.model.FilterPromotionRequest;
import com.example.coffeeshop.model.Promotion;
import com.example.coffeeshop.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/all-promotion")
    public ResponseEntity<List<PromotionDto>> getAllPromotion() {
        try {
            List<PromotionDto> promotions = promotionService.findAll();
            return ResponseEntity.ok(promotions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/filter-promotion")
    public ResponseEntity<List<String>> filterPromotion(@RequestBody FilterPromotionRequest filterPromotionRequest) {
        try {
            List<String> promotions = promotionService.filterPromotions(filterPromotionRequest);
            return ResponseEntity.ok(promotions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
