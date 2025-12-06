package com.example.quizapp.controller;

import com.example.quizapp.dto.*;
import com.example.quizapp.entity.*;
import com.example.quizapp.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<?> createQuiz(@Valid @RequestBody QuizRequest req) {
        Quiz saved = quizService.createQuiz(req);
        return ResponseEntity.ok(Map.of("id", saved.getId()));
    }

    @GetMapping
    public ResponseEntity<List<QuizResponse>> allQuizzes() {
        var quizzes = quizService.getAll();
        var resp = quizzes.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Long id) {
        return quizService.getById(id)
                .map(q -> ResponseEntity.ok(toResponse(q)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteById(id);
        return ResponseEntity.ok(Map.of("deleted", true));
    }

    // mapping helper
    private QuizResponse toResponse(Quiz q) {
        QuizResponse qr = new QuizResponse();
        qr.setId(q.getId());
        qr.setTitle(q.getTitle());
        qr.setDescription(q.getDescription());
        qr.setQuestions(q.getQuestions().stream().map(qq -> {
            QuestionResponse qres = new QuestionResponse();
            qres.setId(qq.getId());
            qres.setText(qq.getText());
            qres.setOptions(qq.getOptions().stream().map(o -> {
                OptionResponse or = new OptionResponse();
                or.setId(o.getId());
                or.setText(o.getText());
                or.setCorrect(o.isCorrect()); // remove for public API if not desired
                return or;
            }).collect(Collectors.toList()));
            return qres;
        }).collect(Collectors.toList()));
        return qr;
    }
}
