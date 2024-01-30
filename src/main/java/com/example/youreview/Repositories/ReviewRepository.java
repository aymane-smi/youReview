package com.example.youreview.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.youreview.Models.Entites.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID>{
    
}
