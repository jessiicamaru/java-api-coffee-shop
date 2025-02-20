package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.Likes;
import com.example.coffeeshop.service.CategoryService;
import com.example.coffeeshop.service.LikesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LikesController {
    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/add-like-coffee")
    @ResponseBody
    public int addLikeCoffee(@RequestBody Likes likes) {
        return likesService.addLikeCoffee(likes);
    }
}
