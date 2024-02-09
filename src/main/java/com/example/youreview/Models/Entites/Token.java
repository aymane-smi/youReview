package com.example.youreview.Models.Entites;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "RefreshToken")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    private UUID id;
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
