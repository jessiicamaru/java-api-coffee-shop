package com.example.coffeeshop.repository;

import com.example.coffeeshop.dto.CategoryDto;
import com.example.coffeeshop.model.Category;
import com.example.coffeeshop.model.Coffee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAll(Sort sort);
}
