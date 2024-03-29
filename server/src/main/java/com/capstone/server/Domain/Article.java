package com.capstone.server.Domain;


import com.google.type.DateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Article {
    @Id
    private int id;
    private String atcTitle;
    private String atcWriter;
    private String atcPhotoIn;
    private String atcText;
    @Column(columnDefinition = "char")
    private String difficulty;
    private Date regDt;
    private int cgId;
    private String genre;

    @Builder
    public Article(int id, String atcTitle, String atcWriter, String atcPhotoIn, String atcText, String difficulty, Date regDt, int cgId, String genre, String ansKwd1, String ansKwd2, String ansKwd3, String ansTopic, String ansSmr) {
        this.id = id;
        this.atcTitle = atcTitle;
        this.atcWriter = atcWriter;
        this.atcPhotoIn = atcPhotoIn;
        this.atcText = atcText;
        this.difficulty = difficulty;
        this.regDt = regDt;
        this.cgId = cgId;
        this.genre = genre;
        this.ansKwd1 = ansKwd1;
        this.ansKwd2 = ansKwd2;
        this.ansKwd3 = ansKwd3;
        this.ansTopic = ansTopic;
        this.ansSmr = ansSmr;
    }

    private String ansKwd1;
    private String ansKwd2;
    private String ansKwd3;
    private String ansTopic;
    private String ansSmr;
}
