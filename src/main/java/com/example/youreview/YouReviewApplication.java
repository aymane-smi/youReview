 package com.example.youreview;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.Impl.UserServiceImpl;

@SpringBootApplication
public class YouReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(YouReviewApplication.class, args);
	}

}
