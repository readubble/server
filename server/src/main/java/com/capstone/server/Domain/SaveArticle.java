package com.capstone.server.Domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class SaveArticle {
    @Id
    private int saveNo;
    private int cgNo;
    private int tbArticleId;
    private String tbUserId;
    private String genre;
    private String atcTitle;
    @Column(columnDefinition = "char")
    private String difficulty;
    private String atcPhotoTn;

    @Builder
    public SaveArticle(int saveNo, int cgNo, int tbArticleId, String tbUserId, String genre, String atcTitle, String difficulty, String atcPhotoTn) {
        this.saveNo = saveNo;
        this.cgNo = cgNo;
        this.tbArticleId = tbArticleId;
        this.tbUserId = tbUserId;
        this.genre = genre;
        this.atcTitle = atcTitle;
        this.difficulty = difficulty;
        this.atcPhotoTn = atcPhotoTn;
    }
}
