package com.capstone.server.Service;

import com.capstone.server.Interface.ArticleInterface;
import com.capstone.server.Repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class ArticleServiceTest {
    @Mock
    ArticleRepository articleRepository;
    @InjectMocks
    ArticleService articleService;

    @Test
    void articleList_test(){
        Pageable pageable = PageRequest.of(0, 1);
        List<ArticleInterface> result = new ArrayList<>();

        ArticleInterface article = new ArticleInterface() {
            @Override
            public String getAtcTitle() {
                return "title1";
            }

            @Override
            public String getAtcWriter() {
                return "writer";
            }

            @Override
            public String getAtcPhotoIn() {
                return "http://url.com";
            }

            @Override
            public String getDifficulty() {
                return "D1";
            }

            @Override
            public String getGenre() {
                return "art";
            }
        };
        result.add(article);
        Page<ArticleInterface> page = new PageImpl<>(result);
        when(articleRepository.findByArticleLEFTJOINTbRead(anyString(), anyString(), any()))
                .thenReturn(page);
        List<ArticleInterface> result_list = articleService.articleList("test123", "art", 0, 1);
        assertThat(result_list.get(0).getAtcTitle()).isEqualTo("title1");
    }

}