package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.CategoryDto;
import com.example.coffeeshop.model.Category;
import com.example.coffeeshop.model.Coffee;
import com.example.coffeeshop.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all-category")
    @ResponseBody
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/add-category")
    @ResponseBody
    public ResponseEntity<CategoryDto> addCategory(@RequestBody Category category) {
        CategoryDto categoryDto = categoryService.addCategory(category);
        return ResponseEntity.ok(categoryDto);
    }
}
