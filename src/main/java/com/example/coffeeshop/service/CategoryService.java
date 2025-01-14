package com.example.coffeeshop.service;

import com.example.coffeeshop.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
}