package com.example.coffeeshop.model;

import com.example.coffeeshop.constants.ConditionType;
import com.example.coffeeshop.constants.Operator;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "promotion_conditions")
@Data
public class PromotionCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", nullable = false)
    private ConditionType conditionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator", nullable = false)
    private Operator operator;

    @Column(name = "condition_value", nullable = false)
    private Double conditionValue;
}


