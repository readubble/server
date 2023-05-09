package com.capstone.server.DTO;

import com.capstone.server.Domain.WordQuiz;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordQuizDTO {
    private int quizNo;
    private String quizQuestion;
    private int quizAns;

    @Builder
    public WordQuizDTO(int quizNo, String quizQuestion, int quizAns) {
        this.quizNo = quizNo;
        this.quizQuestion = quizQuestion;
        this.quizAns = quizAns;
    }

    public WordQuiz toEntity(){
        return WordQuiz.builder()
                .quizNo(quizNo)
                .quizQuestion(quizQuestion)
                .quizAns(quizAns).build();
    }
    public WordQuizDTO fromEntity(WordQuiz wordQuiz){
        return WordQuizDTO.builder()
                .quizNo(wordQuiz.getQuizNo())
                .quizQuestion(wordQuiz.getQuizQuestion())
                .quizAns(wordQuiz.getQuizAns()).build();
    }
}
