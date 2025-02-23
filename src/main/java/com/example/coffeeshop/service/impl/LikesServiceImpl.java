package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.dto.CoffeeDto;
import com.example.coffeeshop.dto.LikesDto;
import com.example.coffeeshop.model.Coffee;
import com.example.coffeeshop.model.Likes;
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

    @Override
    public List<LikesDto> findAllByUid(String uid) {
        List<Likes> likes = likesRepository.findAllByUserUid(uid);
        return likes.stream().map(like -> mapToLikesDto(like)).collect(Collectors.toList());

    }

    @Override
    public int addLikeCoffee(Likes likes) {
        return likesRepository.addLikeCoffee(likes);
    }

    private LikesDto mapToLikesDto(Likes likes) {
        return LikesDto.builder()
                .coffees(
                        Collections.singletonList(likes.getCoffee())
                                .stream()
                                .map(coffee -> mapToCoffeeDto(coffee))
                                .collect(Collectors.toList())
                )
                .build();
    }

    private CoffeeDto mapToCoffeeDto(Coffee coffee) {
        return CoffeeDto.builder()
                .coffeeId(coffee.getCoffeeId())
                .coffeeTitle(coffee.getCoffeeTitle())
                .coffeePhotoUrl(coffee.getCoffeePhotoUrl())
                .coffeeCost(coffee.getCoffeeCost())
                .coffeeDescription(coffee.getCoffeeDescription())
                .categoryTitle(coffee.getCategory().getCategoryTitle())
                .categoryId(coffee.getCategory().getCategoryId())
                .build();
    }

}
