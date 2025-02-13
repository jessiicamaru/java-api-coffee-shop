package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.model.User;
import com.example.coffeeshop.repository.UserRepository;
import com.example.coffeeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int insertIgnore(User user) {
        return userRepository.insertIgnore(user);
    }
}
