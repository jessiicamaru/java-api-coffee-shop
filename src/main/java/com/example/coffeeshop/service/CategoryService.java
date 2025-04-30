package com.example.coffeeshop.service;

import com.example.coffeeshop.dto.CategoryDto;
import com.example.coffeeshop.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto addCategory(Category category);
}