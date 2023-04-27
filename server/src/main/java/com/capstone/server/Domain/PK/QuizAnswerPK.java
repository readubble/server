package com.capstone.server.Domain.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuizAnswerPK implements Serializable {
    private int tbQuizTbArticleId;
    private int tbQuizQuizNo;
    private String tbUserId;
}
