package com.example.quizapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class QuizRequest {

    @NotBlank(message = "title is required")
    private String title;

    private String description;

    @NotEmpty(message = "questions list cannot be empty")
    @Valid
    private List<QuestionRequest> questions;
}
