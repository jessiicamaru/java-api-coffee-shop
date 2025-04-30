package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.dto.CategoryDto;
import com.example.coffeeshop.model.Category;
import com.example.coffeeshop.repository.CategoryRepository;
import com.example.coffeeshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "categoryId"));
        return categories.stream().map(this::mapToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto addCategory(Category category) {
        String categoryId = UUID.randomUUID().toString();
        category.setCategoryId(categoryId);
        categoryRepository.save(category);
        return mapToCategoryDto(category);
    }


    private CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle()).build();
    }
}
