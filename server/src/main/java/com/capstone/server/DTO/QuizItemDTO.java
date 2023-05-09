package com.capstone.server.DTO;

import com.capstone.server.Domain.QuizItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class QuizItemDTO {

    private int tbArticleId;
    private int tbQuizNo;
    private int itemNo;
    private String itemValue;

    @Builder
    public QuizItemDTO(int tbArticleId, int tbQuizNo, int itemNo, String itemValue) {
        this.tbArticleId = tbArticleId;
        this.tbQuizNo = tbQuizNo;
        this.itemNo = itemNo;
        this.itemValue = itemValue;
    }

    public QuizItem toEntity(){
        return QuizItem.builder()
                .tbArticleId(tbArticleId)
                .tbQuizNo(tbQuizNo)
                .itemNo(itemNo)
                .itemValue(itemValue).build();

    }
    public QuizItemDTO fromEntity(QuizItem quizItem){
        return QuizItemDTO.builder()
                .tbArticleId(quizItem.getTbArticleId())
                .tbQuizNo(quizItem.getTbQuizNo())
                .itemNo(quizItem.getItemNo())
                .itemValue(quizItem.getItemValue()).build();

    }
}
