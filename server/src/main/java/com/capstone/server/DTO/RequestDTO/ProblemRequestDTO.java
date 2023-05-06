package com.capstone.server.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Getter
@Setter
public class ProblemRequestDTO {
    private String userId;
    private List<String> keyword;
    private String sentence;
    private String summarization;
    private List<Integer> quizId;
    private List<Integer> quizChoice;
    private Time startTime;
    private Time finishTime;
    private Time totalTime;

    @JsonCreator
    @Builder
    public ProblemRequestDTO(@JsonProperty("user_id") String userId, @JsonProperty("keyword") List<String> keyword, @JsonProperty("sentence") String sentence, @JsonProperty("summarization") String summarization, @JsonProperty("quiz_id") List<Integer> quizId, @JsonProperty("quiz_choice") List<Integer> quizChoice, @JsonProperty("quiz_result") List<String> quizResult, @JsonProperty("start_time") Time startTime, @JsonProperty("finish_time") Time finishTime, @JsonProperty("total_time") Time totalTime) {
        this.userId = userId;
        this.keyword = keyword;
        this.sentence = sentence;
        this.summarization = summarization;
        this.quizId = quizId;
        this.quizChoice = quizChoice;
        this.quizResult = quizResult;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.totalTime = totalTime;
    }

    private List<String> quizResult;
}
