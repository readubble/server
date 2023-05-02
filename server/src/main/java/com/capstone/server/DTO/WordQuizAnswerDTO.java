package com.capstone.server.DTO;

import com.capstone.server.Domain.WordQuiz;
import com.capstone.server.Domain.WordQuizAnswer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class WordQuizAnswerDTO {
    private String tbUserId;
    private int tbWordQuizQuizNo;
    private int quizInp;
    private String correctFl;

    @Builder
    public WordQuizAnswerDTO(String tbUserId, int tbWordQuizQuizNo, int quizInp, String correctFl) {
        this.tbUserId = tbUserId;
        this.tbWordQuizQuizNo = tbWordQuizQuizNo;
        this.quizInp = quizInp;
        this.correctFl = correctFl;
    }

    public WordQuizAnswer toEntity(){
        return WordQuizAnswer.builder()
                .tbUserId(tbUserId)
                .tbWordQuizQuizNo(tbWordQuizQuizNo)
                .quizInp(quizInp)
                .correctFl(correctFl).build();

    }
    public WordQuizAnswerDTO fromEntity(WordQuizAnswer wordQuizAnswer){
        return WordQuizAnswerDTO.builder()
                .tbUserId(wordQuizAnswer.getTbUserId())
                .tbWordQuizQuizNo(wordQuizAnswer.getTbWordQuizQuizNo())
                .quizInp(wordQuizAnswer.getQuizInp())
                .correctFl(wordQuizAnswer.getCorrectFl()).build();
    }
}
