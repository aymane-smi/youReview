package com.example.youreview.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.youreview.Models.Dtos.SignedInUser;
import com.example.youreview.Models.Dtos.UserAuthDTO;
import com.example.youreview.Services.Impl.UserServiceImpl;

import jakarta.validation.Valid;




@Controller
@RequestMapping("/api/auth")
public class UserController {
    private final UserServiceImpl userService;
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }
    @PostMapping("/signin")
    public ResponseEntity<SignedInUser> login(@Valid @RequestBody UserAuthDTO user) {
        System.out.println("inside controller");
        return new ResponseEntity<SignedInUser>(userService.signUser(user), HttpStatus.OK);
    }
    
    @PostMapping("/")
    public String test() {
        return "test";
    }
    
    
}
