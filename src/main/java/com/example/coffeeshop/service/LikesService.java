package com.example.coffeeshop.service;

import com.example.coffeeshop.dto.LikesDto;
import com.example.coffeeshop.model.Likes;

import java.util.List;

public interface LikesService {
    List<LikesDto> findAllByUid(String uid);
    int addLikeCoffee(Likes likes);
}
