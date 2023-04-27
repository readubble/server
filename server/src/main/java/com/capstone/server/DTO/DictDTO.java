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
    private String wordMean;
    private String wordEx;

    @Builder
    public DictDTO(int wordNo, String wordNm, String wordMean, String wordEx) {
        this.wordNo = wordNo;
        this.wordNm = wordNm;
        this.wordMean = wordMean;
        this.wordEx = wordEx;
    }

    public Dict toEntity(){
        return Dict.builder()
                .wordNo(wordNo)
                .wordNm(wordNm)
                .wordMean(wordMean)
                .wordEx(wordEx).build();
    }
    public DictDTO fromEntity(Dict dict){
        return DictDTO.builder()
                .wordNo(dict.getWordNo())
                .wordNm(dict.getWordNm())
                .wordMean(dict.getWordMean())
                .wordEx(dict.getWordEx()).build();
    }
}
