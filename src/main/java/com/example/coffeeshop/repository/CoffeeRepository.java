package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Coffee;
import com.example.coffeeshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee, String> {
    List<Coffee> findByCategory(Category category);
    Coffee findByCoffeeId(String coffeeId);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO coffee (coffee_id, coffee_title, coffee_photo_url, coffee_cost, coffee_description, category_id) " +
            "VALUES (UUID(), :#{#coffee.coffeeTitle}, :#{#coffee.coffeePhotoUrl}, :#{#coffee.coffeeCost}, :#{#coffee.coffeeDescription}, :#{#coffee.category.categoryId})",
            nativeQuery = true)
    void saveCoffee(Coffee coffee);


}
