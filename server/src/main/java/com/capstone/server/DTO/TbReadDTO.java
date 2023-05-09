package com.capstone.server.DTO;

import com.capstone.server.Domain.TbRead;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class TbReadDTO {
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
    public TbReadDTO(int tbArticleId, String tbUserId, String saveFl, String solveFl, Time startTime, Time finishTime, Time totalTime, String inpKwd1, String inpKwd2, String inpKwd3, String inpTopic, String inpSmr) {
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

    public TbRead toEntity(){
        return TbRead.builder()
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
    public TbReadDTO fromEntity(com.capstone.server.Domain.TbRead tbRead){
        return TbReadDTO.builder()
                .tbArticleId(tbRead.getTbArticleId())
                .tbUserId(tbRead.getTbUserId())
                .saveFl(tbRead.getSaveFl())
                .solveFl(tbRead.getSolveFl())
                .startTime(tbRead.getStartTime())
                .finishTime(tbRead.getFinishTime())
                .totalTime(tbRead.getTotalTime())
                .inpKwd1(tbRead.getInpKwd1())
                .inpKwd2(tbRead.getInpKwd2())
                .inpKwd3(tbRead.getInpKwd3())
                .inpTopic(tbRead.getInpTopic())
                .inpSmr(tbRead.getInpSmr()).build();
    }
}
