package com.capstone.server.Domain.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuizPK implements Serializable {
    private int tbArticleId;
    private int quizNo;
}
