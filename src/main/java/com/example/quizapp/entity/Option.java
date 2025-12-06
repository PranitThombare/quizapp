package com.example.quizapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "options")
@Data @NoArgsConstructor @AllArgsConstructor
public class Option {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String text;
    private boolean correct; // true if this option is correct

    @ManyToOne @JoinColumn(name = "question_id")
    private Question question;
}
