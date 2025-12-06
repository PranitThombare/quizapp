package com.example.quizapp.dto;
import lombok.Data;
@Data
public class OptionResponse {
    private Long id;
    private String text;
    // note: do NOT return `correct` to clients in production; for admin or test endpoints you may include it
    private boolean correct;
}
