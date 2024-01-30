package com.example.youreview.Services.Impl;

import com.example.youreview.Repositories.ReviewRepository;
import com.example.youreview.Services.ReviewService;

public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }
}
