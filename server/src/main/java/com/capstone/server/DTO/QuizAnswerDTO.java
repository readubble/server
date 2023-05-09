package com.capstone.server.DTO;

import com.capstone.server.Domain.QuizAnswer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizAnswerDTO {
    private int tbArticleId;
    private int tbQuizNo;
    private String tbUserId;
    private int quizInp;
    private String correctFl;

    @Builder
    public QuizAnswerDTO(int tbArticleId, int tbQuizNo, String tbUserId, int quizInp, String correctFl) {
        this.tbArticleId = tbArticleId;
        this.tbQuizNo = tbQuizNo;
        this.tbUserId = tbUserId;
        this.quizInp = quizInp;
        this.correctFl = correctFl;
    }

    public QuizAnswer toEntity(){
        return QuizAnswer.builder()
                .tbArticleId(tbArticleId)
                .tbQuizNo(tbQuizNo)
                .tbUserId(tbUserId)
                .quizInp(quizInp)
                .correctFl(correctFl).build();
    }
    public QuizAnswerDTO fromEntity(QuizAnswer quizAnswer){
        return QuizAnswerDTO.builder()
                .tbArticleId(quizAnswer.getTbArticleId())
                .tbQuizNo(quizAnswer.getTbQuizNo())
                .tbUserId(quizAnswer.getTbUserId())
                .quizInp(quizAnswer.getQuizInp())
                .correctFl(quizAnswer.getCorrectFl()).build();
    }
}
