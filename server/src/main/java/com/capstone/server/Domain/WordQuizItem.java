package com.capstone.server.Domain;


import com.capstone.server.Domain.PK.WordQuizItemPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@IdClass(WordQuizItemPK.class)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class WordQuizItem {
    @Id
    private int wordQuizNo;
    @Id
    private int itemNo;
    private String itemValue;

    @Builder
    public WordQuizItem(int wordQuizNo, int itemNo, String itemValue) {
        this.wordQuizNo = wordQuizNo;
        this.itemNo = itemNo;
        this.itemValue = itemValue;
    }
}
