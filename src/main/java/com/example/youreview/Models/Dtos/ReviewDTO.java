package com.example.youreview.Models.Dtos;

import java.util.UUID;

import com.example.youreview.Models.Enums.Reaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private UUID id;
    @NotBlank(message = "title is required")
    @Size(max = 255, message = "the title can't be more than 255 character")
    private String Title;
    @NotBlank(message = "the message is required")
    private String message;
    @NotBlank(message = "the reaction is required")
    private Reaction reaction;
    private Long user_id;
}
