package com.example.youreview.Services.Impl;

import com.example.youreview.Configurations.Utils.SecurityContext;
import com.example.youreview.Models.Dtos.ReviewDTO;
import com.example.youreview.Models.Entites.Review;
import com.example.youreview.Models.Entites.User;
import com.example.youreview.Models.Enums.Role;
import com.example.youreview.Repositories.ReviewRepository;
import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.ReviewService;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, ModelMapper modelMapper){
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    @PreAuthorize("hasAnyAuthority('USER')")
    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        var user = userRepository.findByUsername(
            SecurityContext.getName()
        ).get();
        var review = modelMapper.map(reviewDTO, Review.class);
        review.setUser(user);
        review.setDate(LocalDate.now());
        
        return modelMapper.map(reviewRepository.save(review), ReviewDTO.class);
    }
    @Override
    public ReviewDTO getReviewById(UUID id) {
        return modelMapper.map(reviewRepository.findById(id).orElse(null), ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return Arrays.asList(modelMapper.map(reviewRepository.findAll(), ReviewDTO[].class));
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Override
    public void deleteReview(UUID id) {
        User user = userRepository.findByUsername(
            SecurityContext.getName()
        ).get();
        Review review = reviewRepository.findById(id).get();
        if(user.getRole() == Role.USER )
            reviewRepository.deleteById(id);
        else if(user.getRole() == Role.ADMIN && review.getUser().getUsername().equals(user.getUsername())){
            reviewRepository.deleteById(id);
        }else{
            throw new RuntimeException("Unauthorized");
        }
    }
    @PreAuthorize("hasAuthority('MODERATOR')")
    @Override
    public void makeClaim(UUID id) {
        System.out.println("security context=>"+SecurityContext.getName());
        var user = userRepository.findByUsername(
            SecurityContext.getName()
        ).get();
        var review = reviewRepository.findById(id).get();
        var list = review.getClaimedUser();
        list.add(user);
        review.setClaimedUser(list);
        reviewRepository.save(review);
    }
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    @Override
    public List<ReviewDTO> calimedReviews() {
        List<Review> claimes  = new ArrayList<>();
        for(Review r:reviewRepository.findAll())
            if(r.getClaimedUser() != null || r.getClaimedUser().size() > 0)
                claimes.add(r);
        return Arrays.asList(modelMapper.map(claimes, ReviewDTO[].class));
    }
}
