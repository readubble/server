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
    private int tbArticleId;
    private String tbUserId;
    private String genre;
    private String atcTitle;
    private String difficulty;
    private String atcPhotoTn;

    @Builder
    public SaveArticleDTO(int saveNo, int cgNo, int tbArticleId, String tbUserId, String genre, String atcTitle, String difficulty, String atcPhotoTn) {
        this.saveNo = saveNo;
        this.cgNo = cgNo;
        this.tbArticleId = tbArticleId;
        this.tbUserId = tbUserId;
        this.genre = genre;
        this.atcTitle = atcTitle;
        this.difficulty = difficulty;
        this.atcPhotoTn = atcPhotoTn;
    }

    public SaveArticle toEntity(){
        return SaveArticle.builder()
                .saveNo(saveNo)
                .cgNo(cgNo)
                .tbArticleId(tbArticleId)
                .tbUserId(tbUserId)
                .genre(genre)
                .atcTitle(atcTitle)
                .difficulty(difficulty)
                .atcPhotoTn(atcPhotoTn).build();
    }
    public SaveArticleDTO fromEntity(SaveArticle saveArticle){
        return SaveArticleDTO.builder()
                .saveNo(saveArticle.getSaveNo())
                .cgNo(saveArticle.getCgNo())
                .tbArticleId(saveArticle.getTbArticleId())
                .tbUserId(saveArticle.getTbUserId())
                .genre(saveArticle.getGenre())
                .atcTitle(saveArticle.getAtcTitle())
                .difficulty(saveArticle.getDifficulty())
                .atcPhotoTn(saveArticle.getAtcPhotoTn()).build();
    }
}
