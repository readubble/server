package com.capstone.server.Service;

import com.capstone.server.DTO.ArticleDTO;
import com.capstone.server.Domain.SaveWord;
import com.capstone.server.Repository.ArticleRepository;
import com.capstone.server.Repository.SaveArticleRepository;
import com.capstone.server.Domain.Article;
import com.capstone.server.Domain.SaveArticle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SaveArticleService {

    private final SaveArticleRepository saveArticleRepository;

    private final ArticleRepository articleRepository;

    @Autowired
    public SaveArticleService(SaveArticleRepository saveArticleRepository, ArticleRepository articleRepository) {
        this.saveArticleRepository = saveArticleRepository;
        this.articleRepository = articleRepository;
    }
//    SaveArticle
//    @Id
//    private int saveNo;
//    private int cgNo;
//    private int tbArticleId;
//    private String tbUserId;
//    private String genre;
//    private String atcTitle;
//    @Column(columnDefinition = "char")
//    private String difficulty;
//    private String atcPhotoTn;
    // 저장
    public void ArticleBookMark(String userId, int articleId) {
        // 1. 먼저, articleId를 이용하여 Article 테이블에서 해당 문제의 정보를 가져옵니다.
        Article article = articleRepository.findById(articleId);
        Boolean saveArticleCheck = saveArticleRepository.existsByTbUserIdAndTbArticleId(userId, articleId);
        // 이후, SaveArticle 테이블에서 현재 사용자가 해당 문제를 이미 북마크한 적이 있는지 확인합니다.
        // findByTbUserIdAndAndTbArticleId 메소드를 이용하여 tbUserId와 tbArticleId가 모두 일치하는 데이터를 검색하고
        // 결과를 saveArticleCheck 변수에 저장합니다.
        if (!saveArticleCheck) {
            // 만약 saveArticleCheck가 null인 경우, 즉 이전에 북마크한 적이 없는 경우 SaveArticle 객체를 생성하여 해당 정보를 저장
            SaveArticle saveArticle = SaveArticle.builder()
                    .tbUserId(userId)
                    .tbArticleId(article.getId())
                    .atcPhotoTn(article.getAtcPhotoIn())
                    .atcTitle(article.getAtcTitle())
                    .cgNo(article.getCgId())
                    .difficulty(article.getDifficulty())
                    .genre(article.getGenre())
                    .build();
            saveArticleRepository.save(saveArticle);
        }else{
            deleteArticle(userId, articleId);
        }
    }

    // 삭제
    public void deleteArticle(String userId, int articleId) {
        Optional<SaveArticle> saveArticles = saveArticleRepository.findByTbUserIdAndAndTbArticleId(userId, articleId);
        if(saveArticles.isPresent()) {
            saveArticleRepository.delete(saveArticles.get());
        }
    }

    // getSavedWords 메서드는 findAllByTbUserId 메서드를 사용하여 해당 사용자가 저장한 단어를 조회
    // 조회
    public List<SaveArticle> getSavedArticle(String userId) {
        return saveArticleRepository.findAllByTbUserId(userId);
    }

    public List<ArticleDTO> saveArticleList(String userId, int cgId){
        List<SaveArticle> saveList = saveArticleRepository.findAllByTbUserIdAndAndCgNo(userId, cgId);
        List<ArticleDTO> result = new ArrayList<>();
        for(int i=0; i<saveList.size(); i++){
            Article article = articleRepository.findById(saveList.get(i).getTbArticleId());
            ArticleDTO articleDTO = ArticleDTO.builder().build();
            result.add(articleDTO.fromEntity(article));
        }
        return result;
    }

}
