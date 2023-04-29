package com.capstone.server.Repository;

import com.capstone.server.Domain.Article;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Interface.ArticleInterface;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    TbReadRepository tbReadRepository;

    static Article article1;
    static Article article2;
    static Article article3;
    static Article article4;
    static TbRead tbRead1;
    static TbRead tbRead2;

    @BeforeAll
    static void setup(){
        article1 = Article.builder()
                .id(1)
                .atcTitle("title1")
                .atcWriter("writer")
                .atcPhotoIn("")
                .difficulty("D1")
                .genre("art").build();
        article2 = Article.builder()
                .id(2)
                .atcTitle("title2")
                .atcWriter("writer")
                .atcPhotoIn("")
                .difficulty("D1")
                .genre("art").build();
        article3 = Article.builder()
                .id(3)
                .atcTitle("title3")
                .atcWriter("writer")
                .atcPhotoIn("")
                .difficulty("D1")
                .genre("music").build();
        article4 = Article.builder()
                .id(4)
                .atcTitle("title4")
                .atcWriter("writer")
                .atcPhotoIn("")
                .difficulty("D1")
                .genre("music").build();
        tbRead1 = TbRead.builder()
                .tbArticleId(1)
                .tbUserId("test123").build();
        tbRead2 = TbRead.builder()
                .tbArticleId(3)
                .tbUserId("test123").build();
    }

    @Test
    void findByArticleLEFTJOINTbRead_test(){
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);
        tbReadRepository.save(tbRead1);
        tbReadRepository.save(tbRead2);

        Pageable pageable = PageRequest.of(0, 2);
        List<ArticleInterface> result_1 = articleRepository.findByArticleLEFTJOINTbRead("test123", "art", pageable).toList();
        List<ArticleInterface> result_2 = articleRepository.findByArticleLEFTJOINTbRead("test123", "music", pageable).toList();

        assertThat(result_1.get(0).getAtcTitle()).isEqualTo("title2");
        assertThat(result_2.get(0).getAtcTitle()).isEqualTo("title4");
    }

}