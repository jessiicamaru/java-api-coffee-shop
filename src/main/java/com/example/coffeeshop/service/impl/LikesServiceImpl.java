package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.dto.CoffeeDto;
import com.example.coffeeshop.dto.LikesDto;
import com.example.coffeeshop.model.Coffee;
import com.example.coffeeshop.model.Likes;
import com.example.coffeeshop.repository.CoffeeRepository;
import com.example.coffeeshop.repository.LikesRepository;
import com.example.coffeeshop.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public List<String> findAllByUid(String uid) {
        List<Likes> likes = likesRepository.findAllByUid(uid);
        return likes.stream().map(Likes::getCoffeeId).collect(Collectors.toList());

    }

    @Override
    public int addLikeCoffee(Likes likes) {
        return likesRepository.addLikeCoffee(likes);
    }

    @Override
    public int deleteLikeCoffee(String coffeeId, String uid) {
        return likesRepository.deleteLikeCoffeeByCoffeeId(coffeeId, uid);
    }

}
