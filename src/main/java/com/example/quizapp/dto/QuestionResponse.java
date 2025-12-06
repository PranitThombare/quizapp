package com.example.quizapp.dto;
import lombok.Data;
import java.util.List;
@Data
public class QuestionResponse {
    private Long id;
    private String text;
    private List<OptionResponse> options;
}
