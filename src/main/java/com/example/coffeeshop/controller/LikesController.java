package com.example.coffeeshop.controller;

import com.example.coffeeshop.model.Likes;
import com.example.coffeeshop.service.LikesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
    public List<String> getAllLikeByUid(@RequestParam("uid") String uid) {
        return likesService.findAllByUid(uid);
    }

    @DeleteMapping("/delete-by-coffee-id/{id}/{uid}")
    @ResponseBody
    public int deleteLikeCoffee(@PathVariable String id, @PathVariable String uid) {
        return likesService.deleteLikeCoffee(id, uid);
    }
}
