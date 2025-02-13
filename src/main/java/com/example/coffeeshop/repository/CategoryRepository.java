package com.example.coffeeshop.repository;

import com.example.coffeeshop.dto.CategoryDto;
import com.example.coffeeshop.model.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAll(Sort sort);
}
