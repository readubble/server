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
    private int tbArticleId;
    @Id
    private int tbQuizNo;
    @Id
    private String tbUserId;
    private int quizInp;
    @Column(columnDefinition = "char")
    private String correctFl;


    @Builder
    public QuizAnswer(int tbArticleId, int tbQuizNo, String tbUserId, int quizInp, String correctFl) {
        this.tbArticleId = tbArticleId;
        this.tbQuizNo = tbQuizNo;
        this.tbUserId = tbUserId;
        this.quizInp = quizInp;
        this.correctFl = correctFl;
    }

}
