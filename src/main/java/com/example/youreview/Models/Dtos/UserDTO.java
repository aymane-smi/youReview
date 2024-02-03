package com.example.youreview.Models.Dtos;

import java.util.List;

import com.example.youreview.Models.Enums.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "the role is required")
    private Role role;
    private List<ReviewDTO> reviews; 
}
