package com.example.coffeeshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_promotions")
@IdClass(OrderPromotionId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPromotion {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @Id
    @Column(name = "promotion_id")
    private String promotionId;
}