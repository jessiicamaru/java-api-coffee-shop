package com.example.coffeeshop.controller;

import com.example.coffeeshop.dto.LikesDto;
import com.example.coffeeshop.model.Likes;
import com.example.coffeeshop.service.CategoryService;
import com.example.coffeeshop.service.LikesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/like-by-uid")
    @ResponseBody
    public List<LikesDto> getAllLikeByUid(@RequestParam("uid") String uid) {
        return likesService.findAllByUid(uid);
    }
}
