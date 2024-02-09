package com.example.youreview.Services;

import com.example.youreview.Models.Dtos.ReviewDTO;
import com.example.youreview.Models.Entites.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewDTO saveReview(Review review);

    ReviewDTO getReviewById(UUID id);

    List<ReviewDTO> getAllReviews();

    void deleteReview(UUID id);

    void makeClaim(UUID id);

    List<ReviewDTO> calimedReviews();
}
