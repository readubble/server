package com.capstone.server.DTO;

import com.capstone.server.Domain.QuizAnswer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class QuizAnswerDTO {
    private int tbQuizTbArticleId;
    private int tbQuizQuizNo;
    private String tbUserId;
    private int quizInp;
    private String correctFl;

    @Builder
    public QuizAnswerDTO(int tbQuizTbArticleId, int tbQuizQuizNo, String tbUserId, int quizInp, String correctFl) {
        this.tbQuizTbArticleId = tbQuizTbArticleId;
        this.tbQuizQuizNo = tbQuizQuizNo;
        this.tbUserId = tbUserId;
        this.quizInp = quizInp;
        this.correctFl = correctFl;
    }

    public QuizAnswer toEntity(){
        return QuizAnswer.builder()
                .tbQuizTbArticleId(tbQuizTbArticleId)
                .tbQuizQuizNo(tbQuizQuizNo)
                .tbUserId(tbUserId)
                .quizInp(quizInp)
                .correctFl(correctFl).build();
    }
    public QuizAnswerDTO fromEntity(QuizAnswer quizAnswer){
        return QuizAnswerDTO.builder()
                .tbQuizTbArticleId(quizAnswer.getTbQuizTbArticleId())
                .tbQuizQuizNo(quizAnswer.getTbQuizQuizNo())
                .tbUserId(quizAnswer.getTbUserId())
                .quizInp(quizAnswer.getQuizInp())
                .correctFl(quizAnswer.getCorrectFl()).build();
    }
}
