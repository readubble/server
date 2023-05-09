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

    @Builder
    public DictDTO(int wordNo, String wordNm, String wordMean) {
        this.wordNo = wordNo;
        this.wordNm = wordNm;
        this.wordMean = wordMean;
    }

    public Dict toEntity(){
        return Dict.builder()
                .wordNo(wordNo)
                .wordNm(wordNm)
                .wordMean(wordMean).build();
    }
    public DictDTO fromEntity(Dict dict){
        return DictDTO.builder()
                .wordNo(dict.getWordNo())
                .wordNm(dict.getWordNm())
                .wordMean(dict.getWordMean()).build();
    }
}
