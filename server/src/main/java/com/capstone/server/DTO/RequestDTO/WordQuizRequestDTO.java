package com.capstone.server.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WordQuizRequestDTO {
    private String userId;
    private int quizId;
    private int quizChoice;
    private String quizResult;

    @JsonCreator
    @Builder
    public WordQuizRequestDTO(@JsonProperty("user_id") String userId, @JsonProperty("quiz_id") int quizId, @JsonProperty("quiz_choice") int quizChoice, @JsonProperty("quiz_result") String quizResult) {
        this.userId = userId;
        this.quizId = quizId;
        this.quizChoice = quizChoice;
        this.quizResult = quizResult;
    }
}
