package com.example.youreview.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.youreview.Models.Entites.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
