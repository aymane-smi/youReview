package com.example.youreview.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.youreview.Models.Dtos.ReviewDTO;
import com.example.youreview.Services.ReviewService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> reviews(){
        return new ResponseEntity<List<ReviewDTO>>(reviewService.getAllReviews(), HttpStatus.OK);
    }
    // @PostMapping("/add")
    // public ResponseEntity<ReviewDTO> addPostReview(@Valid @RequestBody ReviewDTO review){
    //     return new ResponseEntity<ReviewDTO>(reviewService.saveReview(review), HttpStatus.CREATED);
    // }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<String>("deleted", HttpStatus.ACCEPTED);
    }

    // @PostMapping("/claim/{id}")
    // public ResponseEntity<String> addClaim(@PathVariable UUID id){
    //     reviewService.makeClaim(id);
    //     return new ResponseEntity<String>("claim made", HttpStatus.OK);
    // }

    @GetMapping("/claimes")
    public ResponseEntity<List<ReviewDTO>> showClaimes(Model model){
        return new ResponseEntity<List<ReviewDTO>>(reviewService.calimedReviews(), HttpStatus.OK);
    }
    
}
