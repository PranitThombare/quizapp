package com.example.quizapp.dto;
import lombok.Data;

@Data
public class AnswerRequest {
    private Long questionId;
    private Long selectedOptionId;
}
