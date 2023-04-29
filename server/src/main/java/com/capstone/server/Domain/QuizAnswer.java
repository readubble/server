package com.capstone.server.Domain;


import com.capstone.server.Domain.PK.QuizAnswerPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@IdClass(QuizAnswerPK.class)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class QuizAnswer {
    @Id
    private int tbQuizTbArticleId;
    @Id
    private int tbQuizQuizNo;
    @Id
    private String tbUserId;
    private int quizInp;
    private String correctFl;


    @Builder
    public QuizAnswer(int tbQuizTbArticleId, int tbQuizQuizNo, String tbUserId, int quizInp, String correctFl) {
        this.tbQuizTbArticleId = tbQuizTbArticleId;
        this.tbQuizQuizNo = tbQuizQuizNo;
        this.tbUserId = tbUserId;
        this.quizInp = quizInp;
        this.correctFl = correctFl;
    }

}
