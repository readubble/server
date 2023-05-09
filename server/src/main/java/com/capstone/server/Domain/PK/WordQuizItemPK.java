package com.capstone.server.Domain.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class WordQuizItemPK implements Serializable {
    int wordQuizNo;
    int itemNo;
}
