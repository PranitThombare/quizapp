package com.example.quizapp.controller;

import com.example.quizapp.dto.*;
import com.example.quizapp.entity.Attempt;
import com.example.quizapp.service.AttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class AttemptController {
    private final AttemptService attemptService;

    @PostMapping("/submit")
    public ResponseEntity<AttemptResponse> submit(@Valid @RequestBody AttemptRequest req) {
        Attempt saved = attemptService.submitAttempt(req);
        AttemptResponse resp = toResp(saved);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/me")
    public ResponseEntity<List<AttemptResponse>> myAttempts() {
        var list = attemptService.getAttemptsForCurrentUser();
        var resp = list.stream().map(this::toResp).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    private AttemptResponse toResp(Attempt a) {
        AttemptResponse r = new AttemptResponse();
        r.setId(a.getId());
        r.setQuizId(a.getQuiz() != null ? a.getQuiz().getId() : null);
        r.setScore(a.getScore());
        r.setAttemptedAt(a.getAttemptedAt());
        return r;
    }
}
