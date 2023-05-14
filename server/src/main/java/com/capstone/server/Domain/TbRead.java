package com.capstone.server.Domain;


import com.capstone.server.Domain.PK.TbReadsPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@IdClass(TbReadsPK.class)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class TbRead {
    @Id
    private String tbUserId;
    @Id
    private int tbArticleId;
    @Column(columnDefinition = "char")
    private String saveFl;
    @Column(columnDefinition = "char")
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
    public TbRead(int tbArticleId, String tbUserId, String saveFl, String solveFl, Time startTime, Time finishTime, Time totalTime, String inpKwd1, String inpKwd2, String inpKwd3, String inpTopic, String inpSmr) {
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

    public void setSaveFl(String saveFl) {
        this.saveFl = saveFl;
    }
}

