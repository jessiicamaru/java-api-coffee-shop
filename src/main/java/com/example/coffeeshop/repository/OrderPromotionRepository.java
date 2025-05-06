package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.OrderPromotion;
import com.example.coffeeshop.model.OrderPromotionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPromotionRepository extends JpaRepository<OrderPromotion, OrderPromotionId> {
}