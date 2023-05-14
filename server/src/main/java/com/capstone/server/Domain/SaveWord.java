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
    private String tbUserId;
    private int targetCode;
    private String wordNm;

    @Builder
    public SaveWord(int saveNo, String tbUserId, int targetCode, String wordNm) {
        this.saveNo = saveNo;
        this.tbUserId = tbUserId;
        this.targetCode = targetCode;
        this.wordNm = wordNm;
    }
}
