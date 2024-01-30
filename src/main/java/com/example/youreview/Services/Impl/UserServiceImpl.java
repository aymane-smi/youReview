package com.example.youreview.Services.Impl;

import org.springframework.stereotype.Service;

import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.UserService;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
