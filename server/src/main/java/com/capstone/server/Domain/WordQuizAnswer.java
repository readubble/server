package com.capstone.server.Domain;

import com.capstone.server.Domain.PK.WordQuizAnswerPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@IdClass(WordQuizAnswerPK.class)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class WordQuizAnswer {
    @Id
    private String tbUserId;
    @Id
    private int tbWordQuizQuizNo;
    private int quizInp;
    private String correctFl;

    @Builder
    public WordQuizAnswer(String tbUserId, int tbWordQuizQuizNo, int quizInp, String correctFl) {
        this.tbUserId = tbUserId;
        this.tbWordQuizQuizNo = tbWordQuizQuizNo;
        this.quizInp = quizInp;
        this.correctFl = correctFl;
    }
}
