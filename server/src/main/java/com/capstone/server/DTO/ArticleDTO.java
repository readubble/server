package com.capstone.server.DTO;

import com.capstone.server.Domain.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDTO {
    private int id;
    private String atcTitle;
    private String atcWriter;
    private String atcPhotoIn;
    private String atcText;
    private String difficulty;
    private String regDt;
    private int cgId;
    private String genre;

    @Builder
    public ArticleDTO(int id, String atcTitle, String atcWriter, String atcPhotoIn, String atcText, String difficulty, String regDt, int cgId, String genre) {
        this.id = id;
        this.atcTitle = atcTitle;
        this.atcWriter = atcWriter;
        this.atcPhotoIn = atcPhotoIn;
        this.atcText = atcText;
        this.difficulty = difficulty;
        this.regDt = regDt;
        this.cgId = cgId;
        this.genre = genre;
    }

    public Article toEntity(){
        return Article.builder()
                .id(id)
                .atcTitle(atcTitle)
                .atcWriter(atcWriter)
                .atcPhotoIn(atcPhotoIn)
                .atcText(atcText)
                .difficulty(difficulty)
                .regDt(regDt)
                .cgId(cgId)
                .genre(genre).build();
    }
    public ArticleDTO fromEntity(Article article){
        return ArticleDTO.builder()
                .id(article.getId())
                .atcTitle(article.getAtcTitle())
                .atcWriter(article.getAtcWriter())
                .atcPhotoIn(article.getAtcPhotoIn())
                .atcText(article.getAtcText())
                .difficulty(article.getDifficulty())
                .regDt(article.getRegDt())
                .cgId(article.getCgId())
                .genre(article.getGenre()).build();

    }
}
