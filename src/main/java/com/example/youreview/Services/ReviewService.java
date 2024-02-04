package com.example.youreview.Services;

import com.example.youreview.Models.Entites.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    Review saveReview(Review review);

    Review getReviewById(UUID id);

    List<Review> getAllReviews();

    void deleteReview(UUID id);

    void makeClaim(UUID id);

    List<Review> calimedReviews();
}
