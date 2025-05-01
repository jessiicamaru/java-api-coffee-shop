package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.PromotionUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionUsageRepository extends JpaRepository<PromotionUsage, Long> {
    @Query("SELECT COUNT(pu) FROM PromotionUsage pu WHERE pu.promotionId = :promotionId AND pu.uid = :uid")
    int countUsageByPromotionAndUser(String promotionId, String uid);
}
