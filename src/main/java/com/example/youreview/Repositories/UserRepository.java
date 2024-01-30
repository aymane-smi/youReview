package com.example.youreview.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.youreview.Models.Entites.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
