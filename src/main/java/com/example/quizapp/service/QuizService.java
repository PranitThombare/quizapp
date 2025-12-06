package com.example.quizapp.service;

import com.example.quizapp.dto.*;
import com.example.quizapp.entity.*;
import com.example.quizapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepo;

    @Transactional
    public Quiz createQuiz(QuizRequest req) {
        Quiz quiz = new Quiz();
        quiz.setTitle(req.getTitle());
        quiz.setDescription(req.getDescription());

        if (req.getQuestions() != null) {
            List<Question> questions = req.getQuestions().stream().map(qr -> {
                Question q = new Question();
                q.setText(qr.getText());
                q.setQuiz(quiz);
                if (qr.getOptions() != null) {
                    List<Option> opts = qr.getOptions().stream().map(or -> {
                        Option o = new Option();
                        o.setText(or.getText());
                        o.setCorrect(or.isCorrect());
                        o.setQuestion(q);
                        return o;
                    }).collect(Collectors.toList());
                    q.setOptions(opts);
                }
                return q;
            }).collect(Collectors.toList());
            quiz.setQuestions(questions);
        }

        return quizRepo.save(quiz);
    }

    public List<Quiz> getAll() { return quizRepo.findAll(); }

    public Optional<Quiz> getById(Long id) { return quizRepo.findById(id); }

    @Transactional
    public void deleteById(Long id) { quizRepo.deleteById(id); }
}
