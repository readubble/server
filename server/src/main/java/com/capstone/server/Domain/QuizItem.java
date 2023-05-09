package com.capstone.server.Domain;


import com.capstone.server.Domain.PK.QuizItemPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@IdClass(QuizItemPK.class)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class QuizItem {
    @Id
    private int tbArticleId;
    @Id
    private int tbQuizNo;
    @Id
    private int itemNo;
    private String itemValue;

    @Builder
    public QuizItem(int tbArticleId, int tbQuizNo, int itemNo, String itemValue) {
        this.tbArticleId = tbArticleId;
        this.tbQuizNo = tbQuizNo;
        this.itemNo = itemNo;
        this.itemValue = itemValue;
    }
}
