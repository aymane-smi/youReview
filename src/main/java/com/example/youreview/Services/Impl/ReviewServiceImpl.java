package com.example.youreview.Services.Impl;

import com.example.youreview.Models.Entites.Review;
import com.example.youreview.Repositories.ReviewRepository;
import com.example.youreview.Services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(UUID id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void deleteReview(UUID id) {
        reviewRepository.deleteById(id);
    }
}
