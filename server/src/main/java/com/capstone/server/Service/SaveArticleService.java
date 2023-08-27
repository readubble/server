package com.capstone.server.Service;

import com.capstone.server.DTO.ArticleDTO;
import com.capstone.server.Repository.ArticleRepository;
import com.capstone.server.Repository.SaveArticleRepository;
import com.capstone.server.Domain.Article;
import com.capstone.server.Domain.SaveArticle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void bookmarkArticle(String userId, int articleId) {
        Article article = articleRepository.findById(articleId);
        Boolean saveArticleCheck = saveArticleRepository.existsByTbUserIdAndTbArticleId(userId, articleId);

        if (!saveArticleCheck) {
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
            deleteBookmarkArticle(userId, articleId);
        }
    }

    public void deleteBookmarkArticle(String userId, int articleId) {
        Optional<SaveArticle> saveArticles = saveArticleRepository.findByTbUserIdAndAndTbArticleId(userId, articleId);
        if(saveArticles.isPresent()) {
            saveArticleRepository.delete(saveArticles.get());
        }
    }

    public List<ArticleDTO> getBookmarkArticles(String userId, int cgId){
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
