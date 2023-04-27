package com.capstone.server.Domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class SaveArticle {
    @Id
    private int saveNo;
    private int cgNo;
    private int fkTbArticleId;
    private String userId;
    private String genre;
    private String atcTitle;
    private String difficulty;
    private String atcPhotoTn;

    @Builder
    public SaveArticle(int saveNo, int cgNo, int fkTbArticleId, String userId, String genre, String atcTitle, String difficulty, String atcPhotoTn) {
        this.saveNo = saveNo;
        this.cgNo = cgNo;
        this.fkTbArticleId = fkTbArticleId;
        this.userId= userId;
        this.genre = genre;
        this.atcTitle = atcTitle;
        this.difficulty = difficulty;
        this.atcPhotoTn = atcPhotoTn;
    }
}
