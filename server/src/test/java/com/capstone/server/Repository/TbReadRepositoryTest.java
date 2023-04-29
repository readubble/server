package com.capstone.server.Repository;

import com.capstone.server.DTO.TbReadDTO;
import com.capstone.server.Domain.Article;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Interface.TbReadInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class TbReadRepositoryTest {
    @Autowired
    TbReadRepository tbReadRepository;
    @Autowired
    ArticleRepository articleRepository;

    static com.capstone.server.Domain.TbRead tbRead1;
    static com.capstone.server.Domain.TbRead tbRead2;
    static com.capstone.server.Domain.TbRead tbRead3;
    static com.capstone.server.Domain.TbRead tbRead4;
    static com.capstone.server.Domain.TbRead tbRead5;
    static com.capstone.server.Domain.TbRead tbRead6;
    static Article article1;
    static Article article2;
    static Article article3;
    static Article article4;
    static Article article5;
    static Article article6;

    @BeforeAll
    static void setup(){
        tbRead1 = TbRead.builder()
                .tbUserId("test1234")
                .saveFl("Y")
                .solveFl("Y")
                .startTime(Time.valueOf("00:11:00"))
                .finishTime(Time.valueOf("00:12:00"))
                .totalTime(Time.valueOf("00:01:00"))
                .inpSmr("")
                .inpTopic("")
                .inpKwd1("")
                .inpKwd2("")
                .inpKwd3("")
                .tbArticleId(1).build();
        tbRead2 = TbRead.builder()
                .tbUserId("test1234")
                .saveFl("Y")
                .solveFl("Y")
                .startTime(Time.valueOf("00:11:00"))
                .finishTime(Time.valueOf("00:12:00"))
                .totalTime(Time.valueOf("00:01:00"))
                .inpSmr("")
                .inpTopic("")
                .inpKwd1("")
                .inpKwd2("")
                .inpKwd3("")
                .tbArticleId(2).build();
        tbRead3 = TbRead.builder()
                .tbUserId("test1234")
                .saveFl("Y")
                .solveFl("Y")
                .startTime(Time.valueOf("00:11:00"))
                .finishTime(Time.valueOf("00:12:00"))
                .totalTime(Time.valueOf("00:01:00"))
                .inpSmr("")
                .inpTopic("")
                .inpKwd1("")
                .inpKwd2("")
                .inpKwd3("")
                .tbArticleId(3).build();
        tbRead4 = TbRead.builder()
                .tbUserId("test1234")
                .saveFl("Y")
                .solveFl("Y")
                .startTime(Time.valueOf("00:11:00"))
                .finishTime(Time.valueOf("00:12:00"))
                .totalTime(Time.valueOf("00:01:00"))
                .inpSmr("")
                .inpTopic("")
                .inpKwd1("")
                .inpKwd2("")
                .inpKwd3("")
                .tbArticleId(4).build();
        tbRead5 = TbRead.builder()
                .tbUserId("test1235")
                .saveFl("Y")
                .solveFl("Y")
                .startTime(Time.valueOf("00:11:00"))
                .finishTime(Time.valueOf("00:12:00"))
                .totalTime(Time.valueOf("00:01:00"))
                .inpSmr("")
                .inpTopic("")
                .inpKwd1("")
                .inpKwd2("")
                .inpKwd3("")
                .tbArticleId(5).build();
        tbRead6 = TbRead.builder()
                .tbUserId("test1235")
                .saveFl("Y")
                .solveFl("Y")
                .startTime(Time.valueOf("00:11:00"))
                .finishTime(Time.valueOf("00:12:00"))
                .totalTime(Time.valueOf("00:01:00"))
                .inpSmr("")
                .inpTopic("")
                .inpKwd1("")
                .inpKwd2("")
                .inpKwd3("")
                .tbArticleId(6).build();
        article1 = Article.builder()
                .id(1)
                .atcTitle("test1")
                .genre("01")
                .difficulty("D1").build();
        article2 = Article.builder()
                .id(2)
                .atcTitle("test2")
                .genre("01")
                .difficulty("D2").build();
        article3 = Article.builder()
                .id(3)
                .atcTitle("test3")
                .genre("01")
                .difficulty("D1").build();
        article4 = Article.builder()
                .id(4)
                .atcTitle("test4")
                .genre("01")
                .difficulty("D1").build();
        article5 = Article.builder()
                .id(5)
                .atcTitle("test5")
                .genre("01")
                .difficulty("D3").build();
        article6 = Article.builder()
                .id(6)
                .atcTitle("test6")
                .genre("01")
                .difficulty("D2").build();
    }

    @Test
    void findAllBytbUserIdLEFTJOINArticle_test(){
        tbReadRepository.save(tbRead1);
        tbReadRepository.save(tbRead2);
        tbReadRepository.save(tbRead3);
        tbReadRepository.save(tbRead4);
        tbReadRepository.save(tbRead5);
        tbReadRepository.save(tbRead6);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);
        articleRepository.save(article5);
        articleRepository.save(article6);

        Pageable pageable = PageRequest.of(0, 2);
        Page<TbReadInterface> result_1 = tbReadRepository.findAllByTbUserIdLEFTJOINArticle("test1234", "D1", pageable);
        Page<TbReadInterface> result_2 = tbReadRepository.findAllByTbUserIdLEFTJOINArticle("test1234", "D2", pageable);
        Page<TbReadInterface> result_3 = tbReadRepository.findAllByTbUserIdLEFTJOINArticle("test1235", "D2", pageable);
        Page<TbReadInterface> result_4 = tbReadRepository.findAllByTbUserIdLEFTJOINArticle("test1235", "D3", pageable);
        pageable = PageRequest.of(1,2);
        Page<TbReadInterface> result_5 = tbReadRepository.findAllByTbUserIdLEFTJOINArticle("test1234", "D1", pageable);

        assertThat(result_1.getContent().size()).isEqualTo(2);
        assertThat(result_5.getContent().size()).isEqualTo(1);
        assertThat(result_2.getContent().size()).isEqualTo(1);
        assertThat(result_3.getContent().size()).isEqualTo(1);
        assertThat(result_4.getContent().size()).isEqualTo(1);
    }

}