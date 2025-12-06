package com.example.quizapp.service;

import com.example.quizapp.dto.*;
import com.example.quizapp.entity.*;
import com.example.quizapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AttemptService {
    private final AttemptRepository attemptRepo;
    private final UserRepository userRepo;
    private final QuizRepository quizRepo;
    private final OptionRepository optionRepo;

    @Transactional
    public Attempt submitAttempt(AttemptRequest req) {
        // load quiz (optional check)
        Quiz quiz = quizRepo.findById(req.getQuizId())
                .orElseThrow(() -> new NoSuchElementException("Quiz not found"));

        // find user from security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        int score = 0;
        if (req.getAnswers() != null) {
            for (AnswerRequest a : req.getAnswers()) {
                if (a.getSelectedOptionId() == null) continue;
                Option opt = optionRepo.findById(a.getSelectedOptionId()).orElse(null);
                if (opt != null && opt.isCorrect()) score++;
            }
        }

        Attempt attempt = new Attempt();
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setScore(score);
        attempt.setAttemptedAt(java.time.LocalDateTime.now());
        return attemptRepo.save(attempt);
    }

    public List<Attempt> getAttemptsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        // simple query via repository (could add custom repo method)
        return attemptRepo.findAll().stream()
                .filter(a -> a.getUser() != null && a.getUser().getId().equals(user.getId()))
                .toList();
    }
}
