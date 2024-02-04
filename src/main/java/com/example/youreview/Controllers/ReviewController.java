package com.example.youreview.Controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.youreview.Configurations.Utils.SecurityUtils;
import com.example.youreview.Models.Entites.Review;
import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.ReviewService;


@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    public ReviewController(ReviewService reviewService, UserRepository userRepository){
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }
    @GetMapping
    public String reviews(Model model){
        model.addAttribute("user", userRepository.findByUsername(SecurityUtils.getSessionUser()).get());
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "reviews";
    }
    @GetMapping("/add")
    public String addReview(Model model){
        model.addAttribute("review", new Review());
        return "add";
    }
    @PostMapping("/add")
    public String addPostReview(@ModelAttribute Review review){
        reviewService.saveReview(review);
        return "redirect:/reviews";
    }
    @GetMapping("/edit/{id}")
    public String editReview(@PathVariable UUID id, Model model) {
        model.addAttribute("review", reviewService.getReviewById(id));
        return "edit";
    }
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }

    @GetMapping("/claim/{id}")
    public String addClaim(@PathVariable UUID id){
        reviewService.makeClaim(id);
        return "redirect:/reviews/claimes";
    }

    @GetMapping("/claimes")
    public String showClaimes(Model model){
        model.addAttribute("user", userRepository.findByUsername(SecurityUtils.getSessionUser()).get());
        model.addAttribute("claimes", reviewService.calimedReviews());
        return "claimes";
    }
    
}
