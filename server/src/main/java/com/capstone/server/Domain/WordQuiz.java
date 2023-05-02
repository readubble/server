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
public class WordQuiz {
    @Id
    private int quizNo;
    private String quizQuestion;
    private int quizAns;

    @Builder
    public WordQuiz(int quizNo, String quizQuestion, int quizAns) {
        this.quizNo = quizNo;
        this.quizQuestion = quizQuestion;
        this.quizAns = quizAns;
    }
}
