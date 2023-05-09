package com.capstone.server.Domain.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuizItemPK implements Serializable {
    private int tbArticleId;
    private int tbQuizNo;
    private int itemNo;
}
