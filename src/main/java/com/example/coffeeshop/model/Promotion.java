package com.example.coffeeshop.model;

import com.example.coffeeshop.constants.PromotionStatus;
import com.example.coffeeshop.constants.PromotionType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "promotions")
@Data
public class Promotion {
    @Id
    @Column(name = "promotion_id")
    private String promotionId;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PromotionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_type", nullable = false)
    private PromotionType promotionType;

    @Column(name = "usage_limit_per_user")
    private int usageLimitPerUser;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PromotionCondition> conditions;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PromotionDiscount> discounts;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PromotionEligibleUser> eligibleUsers;
}




