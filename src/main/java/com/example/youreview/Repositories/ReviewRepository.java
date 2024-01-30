package com.example.youreview.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.youreview.Models.Entites.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>{
    
}
