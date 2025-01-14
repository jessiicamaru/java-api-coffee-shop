package com.example.coffeeshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
@Data
public class Category {
    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_title")
    private String categoryTitle;
}
