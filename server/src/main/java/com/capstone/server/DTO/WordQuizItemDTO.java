package com.capstone.server.DTO;

import com.capstone.server.Domain.WordQuizItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordQuizItemDTO {
    private int wordQuizNo;
    private int itemNo;
    private String itemValue;

    @Builder
    public WordQuizItemDTO(int wordQuizNo, int itemNo, String itemValue) {
        this.wordQuizNo = wordQuizNo;
        this.itemNo = itemNo;
        this.itemValue = itemValue;
    }

    public WordQuizItem toEntity(){
        return WordQuizItem.builder()
                .wordQuizNo(wordQuizNo)
                .itemNo(itemNo)
                .itemValue(itemValue).build();
    }
    public WordQuizItemDTO fromEntity(WordQuizItem wordQuizItem){
        return WordQuizItemDTO.builder()
                .wordQuizNo(wordQuizItem.getWordQuizNo())
                .itemNo(wordQuizItem.getItemNo())
                .itemValue(wordQuizItem.getItemValue()).build();
    }
}
