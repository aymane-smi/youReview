package com.example.youreview.Services.Impl;

import com.example.youreview.Configurations.Utils.SecurityUtils;
import com.example.youreview.Models.Entites.Review;
import com.example.youreview.Repositories.ReviewRepository;
import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Review saveReview(Review review) {
        var user = userRepository.findByUsername(
            SecurityUtils.getSessionUser()
        ).get();
        review.setUser(user);
        review.setDate(LocalDate.now());
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

    @Override
    public void makeClaim(UUID id) {
        var user = userRepository.findByUsername(
            SecurityUtils.getSessionUser()
        ).get();
        var review = reviewRepository.findById(id).get();
        var list = review.getClaimedUser();
        list.add(user);
        review.setClaimedUser(list);
        reviewRepository.save(review);
    }

    @Override
    public List<Review> calimedReviews() {
        List<Review> claimes  = new ArrayList<>();
        for(Review r:reviewRepository.findAll())
            if(r.getClaimedUser() != null || r.getClaimedUser().size() > 0)
                claimes.add(r);
        return claimes;
    }
}
