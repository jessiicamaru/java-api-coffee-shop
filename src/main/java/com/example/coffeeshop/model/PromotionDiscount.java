package com.example.coffeeshop.model;

import com.example.coffeeshop.constants.DiscountTarget;
import com.example.coffeeshop.constants.DiscountType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "promotion_discounts")
@Data
public class PromotionDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_target", nullable = false)
    private DiscountTarget discountTarget;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false)
    private Double discountValue;
}

