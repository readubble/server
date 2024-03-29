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
public class Dict {
    @Id
    private int wordNo;
    private String wordNm;
    private String wordMean;
    private int targetCode;

    @Builder
    public Dict(int wordNo, String wordNm, String wordMean, int targetCode) {
        this.wordNo = wordNo;
        this.wordNm = wordNm;
        this.wordMean = wordMean;
        this.targetCode = targetCode;
    }
}
