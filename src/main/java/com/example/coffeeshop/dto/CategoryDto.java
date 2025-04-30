package com.example.coffeeshop.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private String categoryId;
    private String categoryTitle;
}
