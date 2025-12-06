
package com.example.quizapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {

    @NotBlank(message = "question text is required")
    private String text;

    @NotEmpty(message = "options list required")
    @Valid
    private List<OptionRequest> options;
}


