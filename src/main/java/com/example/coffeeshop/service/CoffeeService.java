package com.example.coffeeshop.service;

import com.example.coffeeshop.dto.CoffeeDto;
import com.example.coffeeshop.model.Coffee;

import java.util.List;
import java.util.UUID;

public interface CoffeeService {
    List<CoffeeDto> getAllCoffees();
    void insertCoffee(Coffee coffee);
    List<CoffeeDto> getAllCoffeesByCategory(String categoryId);
    void addLikeCoffee(String id);
}
