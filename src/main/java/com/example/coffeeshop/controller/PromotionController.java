package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.PromotionDto;
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
    public ResponseEntity<List<PromotionDto>> getApplicablePromotions(@RequestParam("uid") String uid) {
        try {
            List<PromotionDto> promotions = promotionService.getApplicablePromotions(uid);
            return ResponseEntity.ok(promotions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
