package com.capstone.server.Domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Quiz {
    @Id
    private int tbArticleId;
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
