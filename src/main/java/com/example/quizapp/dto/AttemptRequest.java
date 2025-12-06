package com.example.quizapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class AttemptRequest {

    @NotNull
    private Long quizId;

    @NotEmpty
    @Valid
    private List<AnswerRequest> answers;
}
