package com.capstone.server.DTO;

import com.capstone.server.Domain.SaveArticle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveArticleDTO {
    private int saveNo;
    private int cgNo;
    private int fkTbArticleId;
    private String userId;
    private String genre;
    private String atcTitle;
    private String difficulty;
    private String atcPhotoTn;

    @Builder
    public SaveArticleDTO(int saveNo, int cgNo, int fkTbArticleId, String userId, String genre, String atcTitle, String difficulty, String atcPhotoTn) {
        this.saveNo = saveNo;
        this.cgNo = cgNo;
        this.fkTbArticleId = fkTbArticleId;
        this.userId = userId;
        this.genre = genre;
        this.atcTitle = atcTitle;
        this.difficulty = difficulty;
        this.atcPhotoTn = atcPhotoTn;
    }

    public SaveArticle toEntity(){
        return SaveArticle.builder()
                .saveNo(saveNo)
                .cgNo(cgNo)
                .fkTbArticleId(fkTbArticleId)
                .userId(userId)
                .genre(genre)
                .atcTitle(atcTitle)
                .difficulty(difficulty)
                .atcPhotoTn(atcPhotoTn).build();
    }
    public SaveArticleDTO fromEntity(SaveArticle saveArticle){
        return SaveArticleDTO.builder()
                .saveNo(saveArticle.getSaveNo())
                .cgNo(saveArticle.getCgNo())
                .fkTbArticleId(saveArticle.getFkTbArticleId())
                .userId(saveArticle.getUserId())
                .genre(saveArticle.getGenre())
                .atcTitle(saveArticle.getAtcTitle())
                .difficulty(saveArticle.getDifficulty())
                .atcPhotoTn(saveArticle.getAtcPhotoTn()).build();
    }
}
