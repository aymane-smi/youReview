package com.example.youreview.Services.Impl;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.youreview.Configurations.Utils.CostumeUserDetails;
import com.example.youreview.Models.Dtos.SignedInUser;
import com.example.youreview.Models.Dtos.UserAuthDTO;
import com.example.youreview.Models.Entites.User;
import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.JwtService;
import com.example.youreview.Services.UserService;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CostumeUserDetails userDetails;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, CostumeUserDetails userDetails){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetails = userDetails;
    }
    @Override
    public SignedInUser signUser(UserAuthDTO user) {
        User userJPA = userRepository.findByUsername(user.getUsername()).get();
        if(passwordEncoder.matches(user.getPassword(), userJPA.getPassword())){
            System.out.println("username match");
            String token = jwtService.generateToken(
                userDetails.loadUserByUsername(userJPA.getUsername())
            );
            return new SignedInUser().builder().username(userJPA.getUsername())
                                               .id(userJPA.getId())
                                               .token(token)
                                               .build();
        }else{
            throw new InsufficientAuthenticationException("UnAuthorized");
        }
    }
}
