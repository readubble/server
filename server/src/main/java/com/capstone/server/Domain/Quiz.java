package com.capstone.server.Domain;


import com.capstone.server.Domain.PK.QuizPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;

@Entity
@Getter
@IdClass(QuizPK.class)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Quiz {
    @Id
    private int tbArticleId;
    @Id
    private int quizNo;
    private String quizQuestion;
    private int quizAns;

    @Builder
    public Quiz(int tbArticleId, int quizNo, String quizQuestion, int quizAns) {
        this.tbArticleId = tbArticleId;
        this.quizNo = quizNo;
        this.quizQuestion = quizQuestion;
        this.quizAns = quizAns;
    }
}
