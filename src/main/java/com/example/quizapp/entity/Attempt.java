package com.example.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Attempt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne private User user;
    @ManyToOne private Quiz quiz;
    private int score;
    private LocalDateTime attemptedAt = LocalDateTime.now();
}
