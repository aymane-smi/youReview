package com.example.youreview.Services;

import com.example.youreview.Models.Dtos.ReviewDTO;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewDTO saveReview(ReviewDTO review);

    ReviewDTO getReviewById(UUID id);

    List<ReviewDTO> getAllReviews();

    void deleteReview(UUID id);

    void makeClaim(UUID id);

    List<ReviewDTO> calimedReviews();
}
