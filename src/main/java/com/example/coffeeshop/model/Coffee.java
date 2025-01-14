package com.example.coffeeshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coffee")
@Data
public class Coffee {
    @Id
    private String coffeeId;

    private String coffeeTitle;
    private String coffeePhotoUrl;
    private Double coffeeCost;
    private String coffeeDescription;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id") // ánh xạ với category_id
    private Category category;
}

