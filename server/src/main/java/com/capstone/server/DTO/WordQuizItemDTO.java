package com.capstone.server.DTO;

import com.capstone.server.Domain.WordQuizItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class WordQuizItemDTO {
    private int tbWordQuizQuizNo;
    private int itemNo;
    private String itemValue;

    @Builder
    public WordQuizItemDTO(int tbWordQuizQuizNo, int itemNo, String itemValue) {
        this.tbWordQuizQuizNo = tbWordQuizQuizNo;
        this.itemNo = itemNo;
        this.itemValue = itemValue;
    }

    public WordQuizItem toEntity(){
        return WordQuizItem.builder()
                .tbWordQuizQuizNo(tbWordQuizQuizNo)
                .itemNo(itemNo)
                .itemValue(itemValue).build();
    }
    public WordQuizItemDTO fromEntity(WordQuizItem wordQuizItem){
        return WordQuizItemDTO.builder()
                .tbWordQuizQuizNo(wordQuizItem.getTbWordQuizQuizNo())
                .itemNo(wordQuizItem.getItemNo())
                .itemValue(wordQuizItem.getItemValue()).build();
    }
}
