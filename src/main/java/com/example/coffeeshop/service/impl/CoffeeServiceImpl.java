package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.dto.CoffeeDto;
import com.example.coffeeshop.model.Category;
import com.example.coffeeshop.model.Coffee;
import com.example.coffeeshop.repository.CategoryRepository;
import com.example.coffeeshop.repository.CoffeeRepository;
import com.example.coffeeshop.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;
    private CategoryRepository categoryRepository;

    public CoffeeServiceImpl(CoffeeRepository coffeeRepository, CategoryRepository categoryRepository) {
        this.coffeeRepository = coffeeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CoffeeDto> getAllCoffees() {
        List<Coffee> coffees = coffeeRepository.findAll();
        return coffees.stream().map(coffee -> mapToCoffeeDto(coffee)).collect(Collectors.toList());
    }

    @Override
    public Coffee insertCoffee(Coffee coffee) {
        return null;
    }

    @Override
    public List<CoffeeDto> getAllCoffeesByCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));

        // Lấy danh sách coffee theo category
        return coffeeRepository.findByCategory(category)
                .stream()
                .map(this::mapToCoffeeDto)
                .collect(Collectors.toList());
    }

    private CoffeeDto mapToCoffeeDto(Coffee coffee) {
        return CoffeeDto.builder()
                .coffeeId(coffee.getCoffeeId())
                .coffeeTitle(coffee.getCoffeeTitle())
                .coffeePhotoUrl(coffee.getCoffeePhotoUrl())
                .coffeeCost(coffee.getCoffeeCost())
                .coffeeDescription(coffee.getCoffeeDescription())
                .categoryTitle(coffee.getCategory().getCategoryTitle())
                .build();
    }
}
