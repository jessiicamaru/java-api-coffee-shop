package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Coffee;
import com.example.coffeeshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee, String> {
    List<Coffee> findByCategory(Category category);
}
