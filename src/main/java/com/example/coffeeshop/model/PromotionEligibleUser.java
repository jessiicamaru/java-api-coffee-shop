package com.example.coffeeshop.model;

import com.example.coffeeshop.constants.CriteriaType;
import com.example.coffeeshop.constants.Operator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "promotion_eligible_users")
@Data
public class PromotionEligibleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "criteria_type", nullable = false)
    private CriteriaType criteriaType;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator", nullable = false)
    private Operator operator;

    @Column(name = "criteria_value", nullable = false)
    private String criteriaValue;
}

