package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
    @Query("SELECT p FROM Promotion p WHERE p.status = 'active' AND p.startDate <= :now AND p.endDate >= :now")
    List<Promotion> findActivePromotions(LocalDateTime now);
}
