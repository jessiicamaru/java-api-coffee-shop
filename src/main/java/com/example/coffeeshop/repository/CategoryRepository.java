package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, String> {
}
