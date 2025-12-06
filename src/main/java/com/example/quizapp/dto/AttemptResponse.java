package com.example.quizapp.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AttemptResponse {
    private Long id;
    private Long quizId;
    private int score;
    private LocalDateTime attemptedAt;
}
