package com.capstone.server.DTO;

import com.capstone.server.Domain.Quiz;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDTO {
    private int tbArticleId;
    private int quizNo;
    private String quizQuestion;
    private int quizAns;

    @Builder
    public QuizDTO(int tbArticleId, int quizNo, String quizQuestion, int quizAns) {
        this.tbArticleId = tbArticleId;
        this.quizNo = quizNo;
        this.quizQuestion = quizQuestion;
        this.quizAns = quizAns;
    }

    public Quiz toEntity(){
        return Quiz.builder()
                .tbArticleId(tbArticleId)
                .quizNo(quizNo)
                .quizQuestion(quizQuestion)
                .quizAns(quizAns).build();

    }
    public QuizDTO fromEntity(Quiz quiz){
        return QuizDTO.builder()
                .tbArticleId(quiz.getTbArticleId())
                .quizNo(quiz.getQuizNo())
                .quizQuestion(quiz.getQuizQuestion())
                .quizAns(quiz.getQuizAns()).build();

    }
}
