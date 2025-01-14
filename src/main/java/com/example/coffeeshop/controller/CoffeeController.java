package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.CategoryDto;
import com.example.coffeeshop.dto.CoffeeDto;
import com.example.coffeeshop.model.Category;
import com.example.coffeeshop.service.CategoryService;
import com.example.coffeeshop.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
public class CoffeeController {
    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/all-coffee")
    @ResponseBody
    public List<CoffeeDto> getAllCoffees() {
        return coffeeService.getAllCoffees();
    }

    @GetMapping("/coffee-by-id")
    @ResponseBody
    public List<CoffeeDto> getAllCoffeesByCategory(@RequestParam("id") String categoryId) {
        //http://localhost:5000/all-coffee?id=0d6d2f67-d26b-11
        return coffeeService.getAllCoffeesByCategory(categoryId);
    }

}
