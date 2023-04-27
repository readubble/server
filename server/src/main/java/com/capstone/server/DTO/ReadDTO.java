package com.capstone.server.DTO;

import com.capstone.server.Domain.Read;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class ReadDTO {
    private int tbArticleId;
    private String tbUserId;
    private String saveFl;
    private String solveFl;
    private Time startTime;
    private Time finishTime;
    private Time totalTime;
    private String inpKwd1;
    private String inpKwd2;
    private String inpKwd3;
    private String inpTopic;
    private String inpSmr;

    @Builder
    public ReadDTO(int tbArticleId, String tbUserId, String saveFl, String solveFl, Time startTime, Time finishTime, Time totalTime, String inpKwd1, String inpKwd2, String inpKwd3, String inpTopic, String inpSmr) {
        this.tbArticleId = tbArticleId;
        this.tbUserId = tbUserId;
        this.saveFl = saveFl;
        this.solveFl = solveFl;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.totalTime = totalTime;
        this.inpKwd1 = inpKwd1;
        this.inpKwd2 = inpKwd2;
        this.inpKwd3 = inpKwd3;
        this.inpTopic = inpTopic;
        this.inpSmr = inpSmr;
    }

    public Read toEntity(){
        return Read.builder()
                .tbArticleId(tbArticleId)
                .tbUserId(tbUserId)
                .saveFl(saveFl)
                .solveFl(solveFl)
                .startTime(startTime)
                .finishTime(finishTime)
                .totalTime(totalTime)
                .inpKwd1(inpKwd1)
                .inpKwd2(inpKwd2)
                .inpKwd3(inpKwd3)
                .inpTopic(inpTopic)
                .inpSmr(inpSmr).build();
    }
    public ReadDTO fromEntity(Read read){
        return ReadDTO.builder()
                .tbArticleId(read.getTbArticleId())
                .tbUserId(read.getTbUserId())
                .saveFl(read.getSaveFl())
                .solveFl(read.getSolveFl())
                .startTime(read.getStartTime())
                .finishTime(read.getFinishTime())
                .totalTime(read.getTotalTime())
                .inpKwd1(read.getInpKwd1())
                .inpKwd2(read.getInpKwd2())
                .inpKwd3(read.getInpKwd3())
                .inpTopic(read.getInpTopic())
                .inpSmr(read.getInpSmr()).build();
    }
}
