package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.model.Likes;
import com.example.coffeeshop.repository.LikesRepository;
import com.example.coffeeshop.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    private LikesRepository likesRepository;

    @Override
    public ArrayList<Likes> findAllByUid(String uid) {
        return null;
    }

    @Override
    public int addLikeCoffee(Likes likes) {
        return likesRepository.addLikeCoffee(likes);
    }
}
