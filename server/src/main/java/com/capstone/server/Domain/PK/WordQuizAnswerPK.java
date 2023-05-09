package com.capstone.server.Domain.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class WordQuizAnswerPK implements Serializable {
    String tbUserId;
    int tbWordQuizNo;
}
