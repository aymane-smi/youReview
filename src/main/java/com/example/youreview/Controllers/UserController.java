package com.example.youreview.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.youreview.Models.Entites.User;
import com.example.youreview.Services.Impl.UserServiceImpl;




@Controller
@RequestMapping("/")
public class UserController {
    private final UserServiceImpl userService;
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }
    @GetMapping
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("/reviews")
    public String reviews() {
        return "reviews";
    }
    
    // @PostMapping("/")
    // public String loginPost() {
    //     return "redirect:/reviews";
    // }
    
    
}
