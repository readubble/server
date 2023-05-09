package com.capstone.server.DTO.ResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WordQuizResultDTO {

    private int quizId;
    private String quiz;
    private List<String> choices;
    private int answer;
    private String solved;
    private int solvedChoice;

    @Builder
    public WordQuizResultDTO(int quizId, String quiz, List<String> choices, int answer, String solved, int solvedChoice) {
        this.quizId = quizId;
        this.quiz = quiz;
        this.choices = choices;
        this.answer = answer;
        this.solved = solved;
        this.solvedChoice = solvedChoice;
    }
}
