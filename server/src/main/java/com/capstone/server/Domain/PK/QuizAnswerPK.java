package com.capstone.server.Domain.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuizAnswerPK implements Serializable {
    private int tbArticleId;
    private int tbQuizNo;
    private String tbUserId;
}
