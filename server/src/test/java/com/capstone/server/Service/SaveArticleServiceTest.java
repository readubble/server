package com.capstone.server.Service;

import com.capstone.server.DTO.ArticleDTO;
import com.capstone.server.Domain.Article;
import com.capstone.server.Domain.SaveArticle;
import com.capstone.server.Domain.User;
import com.capstone.server.Repository.ArticleRepository;
import com.capstone.server.Repository.SaveArticleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class SaveArticleServiceTest {
    @Mock
    SaveArticleRepository saveArticleRepository;
    @Mock
    ArticleRepository articleRepository;
    @InjectMocks
    SaveArticleService saveArticleService;

    static Article article1;

    @BeforeAll
    static void setup(){
        article1 = Article.builder()
                .id(1)
                .atcTitle("title1")
                .atcWriter("writer")
                .atcPhotoIn("")
                .difficulty("1")
                .genre("art").build();
    }

    @Test
    void article_bookmark_test(){
        when(articleRepository.findById(1))
                .thenReturn(article1);
        when(saveArticleRepository.existsByTbUserIdAndTbArticleId("test123", 1))
                .thenReturn(true);

        saveArticleService.ArticleBookMark("test123", 1);

        verify(saveArticleRepository, times(1)).save(any(SaveArticle.class));
    }

    @Test
    void delete_article_test(){
        when(saveArticleRepository.findByTbUserIdAndAndTbArticleId("test123", 1))
                .thenReturn(Optional.of(SaveArticle.builder()
                        .tbArticleId(1)
                        .tbUserId("test123")
                        .build()));
        saveArticleService.deleteArticle("test123",1);
        verify(saveArticleRepository, times(1)).delete(any(SaveArticle.class));
    }

    @Test
    void save_article_list_test(){
        when(articleRepository.findById(anyInt()))
                .thenReturn(Article.builder().id(1).build());
        when(saveArticleRepository.findAllByTbUserIdAndAndCgNo("test123", 1))
                .thenReturn(List.of(SaveArticle.builder()
                        .tbUserId("test123")
                        .tbArticleId(1).build()));
        List<ArticleDTO> result = saveArticleService.saveArticleList("test123", 1);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
    }

}