package com.capstone.server.DTO;

import com.capstone.server.Domain.QuizItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class QuizItemDTO {

    private int tbQuizTbArticleId;
    private int tbQuizQuizNo;
    private int itemNo;
    private String itemValue;

    @Builder
    public QuizItemDTO(int tbQuizTbArticleId, int tbQuizQuizNo, int itemNo, String itemValue) {
        this.tbQuizTbArticleId = tbQuizTbArticleId;
        this.tbQuizQuizNo = tbQuizQuizNo;
        this.itemNo = itemNo;
        this.itemValue = itemValue;
    }

    public QuizItem toEntity(){
        return QuizItem.builder()
                .tbQuizTbArticleId(tbQuizTbArticleId)
                .tbQuizQuizNo(tbQuizQuizNo)
                .itemNo(itemNo)
                .itemValue(itemValue).build();

    }
    public QuizItemDTO fromEntity(QuizItem quizItem){
        return QuizItemDTO.builder()
                .tbQuizTbArticleId(quizItem.getTbQuizTbArticleId())
                .tbQuizQuizNo(quizItem.getTbQuizQuizNo())
                .itemNo(quizItem.getItemNo())
                .itemValue(quizItem.getItemValue()).build();

    }
}
