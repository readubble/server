package com.capstone.server.DTO;

import com.capstone.server.Domain.Dict;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.html.parser.Entity;

@Getter
@Setter
public class DictDTO {
    private int wordNo;
    private String wordNm;
    private int targetCode;
    private String wordMean;

    @Builder
    public DictDTO(int wordNo, String wordNm, int targetCode, String wordMean) {
        this.wordNo = wordNo;
        this.wordNm = wordNm;
        this.targetCode = targetCode;
        this.wordMean = wordMean;
    }

    public Dict toEntity(){
        return Dict.builder()
                .wordNo(wordNo)
                .wordNm(wordNm)
                .targetCode(targetCode)
                .wordMean(wordMean).build();
    }
    public DictDTO fromEntity(Dict dict){
        return DictDTO.builder()
                .wordNo(dict.getWordNo())
                .wordNm(dict.getWordNm())
                .targetCode(dict.getTargetCode())
                .wordMean(dict.getWordMean()).build();
    }
}
