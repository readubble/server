package com.capstone.server.DTO;

import com.capstone.server.Domain.WordQuizAnswer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class WordQuizAnswerDTO {
    private String tbUserId;
    private int tbWordQuizNo;
    private int quizInp;
    private String correctFl;

    @Builder
    public WordQuizAnswerDTO(String tbUserId, int tbWordQuizNo, int quizInp, String correctFl) {
        this.tbUserId = tbUserId;
        this.tbWordQuizNo = tbWordQuizNo;
        this.quizInp = quizInp;
        this.correctFl = correctFl;
    }

    public WordQuizAnswer toEntity(){
        return WordQuizAnswer.builder()
                .tbUserId(tbUserId)
                .tbWordQuizNo(tbWordQuizNo)
                .quizInp(quizInp)
                .correctFl(correctFl).build();

    }
    public WordQuizAnswerDTO fromEntity(WordQuizAnswer wordQuizAnswer){
        return WordQuizAnswerDTO.builder()
                .tbUserId(wordQuizAnswer.getTbUserId())
                .tbWordQuizNo(wordQuizAnswer.getTbWordQuizNo())
                .quizInp(wordQuizAnswer.getQuizInp())
                .correctFl(wordQuizAnswer.getCorrectFl()).build();
    }
}
