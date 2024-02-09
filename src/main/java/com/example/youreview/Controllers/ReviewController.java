package com.example.youreview.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.youreview.Configurations.Utils.SecurityUtils;
import com.example.youreview.Models.Dtos.ReviewDTO;
import com.example.youreview.Models.Entites.Review;
import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.ReviewService;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    public ReviewController(ReviewService reviewService, UserRepository userRepository){
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> reviews(){
        return new ResponseEntity<List<ReviewDTO>>(reviewService.getAllReviews(), HttpStatus.OK);
    }
    @GetMapping("/add")
    public String addReview(){
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
