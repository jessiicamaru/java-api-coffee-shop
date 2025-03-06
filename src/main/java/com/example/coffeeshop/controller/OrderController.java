package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.OrderRequest;
import com.example.coffeeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return ResponseEntity.ok("Order created successfully!");
    }

    @GetMapping("/web/orders")
    public String getOrder(Model model) {
        //lấy các order order và trả về dạng sau
        /*
        * {
        *   user : {
        *       name: (display_name trong bảng users)
        *       email:
        *   },
        *   coffees: [
        *       {
        *           coffee_cost:
        *           coffee_photo_Url:
        *           coffee_title:
        *           category_title:
        *           quantity:
        *           size:
        *       }
        *   ]
        * }
        * */
        return "order";
    }
}
