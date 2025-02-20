package com.example.coffeeshop.service;

import com.example.coffeeshop.model.Likes;

import java.util.ArrayList;

public interface LikesService {
    ArrayList<Likes> findAllByUid(String uid);
    int addLikeCoffee(Likes likes);
}
