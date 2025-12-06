package com.example.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OptionRequest {

    @NotBlank(message = "option text is required")
    private String text;

    private boolean correct;
}

