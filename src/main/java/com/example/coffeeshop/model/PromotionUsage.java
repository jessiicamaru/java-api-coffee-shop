package com.example.coffeeshop.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotion_usage")
@Data
public class PromotionUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "promotion_id", nullable = false)
    private String promotionId;

    @Column(name = "uid", nullable = false)
    private String uid;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "used_at", nullable = false)
    private LocalDateTime usedAt;
}