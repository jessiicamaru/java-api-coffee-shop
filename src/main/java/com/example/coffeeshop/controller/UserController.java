package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.User;
import com.example.coffeeshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save-user")
    @ResponseBody
    public int insertIgnore(@RequestBody User user) {
        return userService.insertIgnore(user);
    }
}
