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
public class SaveWord {
    @Id
    private int saveNo;
    private String userId;
    private int fkTbWordNo;
    private String wordNm;

    @Builder
    public SaveWord(int saveNo, String userId, int fkTbWordNo, String wordNm) {
        this.saveNo = saveNo;
        this.userId = userId;
        this.fkTbWordNo = fkTbWordNo;
        this.wordNm = wordNm;
    }
}
