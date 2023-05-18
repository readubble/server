package com.capstone.server.Repository;

import com.capstone.server.Domain.SaveArticle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class SaveArticleRepositoryTest {
    @Autowired
    SaveArticleRepository saveArticleRepository;

    static SaveArticle saveArticle1;
    static SaveArticle saveArticle2;
    static SaveArticle saveArticle3;

    @BeforeAll
    static void setup(){
        saveArticle1 = SaveArticle.builder()
                .saveNo(1)
                .tbArticleId(1)
                .tbUserId("test123")
                .atcTitle("")
                .atcPhotoTn("")
                .cgNo(1)
                .difficulty("1")
                .genre("미술").build();
        saveArticle2 = SaveArticle.builder()
                .saveNo(2)
                .tbArticleId(2)
                .tbUserId("test123")
                .atcTitle("")
                .atcPhotoTn("")
                .cgNo(1)
                .difficulty("1")
                .genre("미술").build();
        saveArticle3 = SaveArticle.builder()
                .saveNo(3)
                .tbArticleId(3)
                .tbUserId("test123")
                .atcTitle("")
                .atcPhotoTn("")
                .cgNo(1)
                .difficulty("1")
                .genre("미술").build();

    }

    @Test
    void findByTbUserIdAndTbArticleId_test(){
        saveArticleRepository.save(saveArticle1);
        saveArticleRepository.save(saveArticle2);
        saveArticleRepository.save(saveArticle3);
        SaveArticle saveArticle = saveArticleRepository.findByTbUserIdAndAndTbArticleId("test123", 1).get();
        assertThat(saveArticle.getTbArticleId()).isEqualTo(1);
        assertThat(saveArticle.getTbUserId()).isEqualTo("test123");
    }

    @Test
    void findAllByTbUserId_test(){
        saveArticleRepository.save(saveArticle1);
        saveArticleRepository.save(saveArticle2);
        saveArticleRepository.save(saveArticle3);
        List<SaveArticle> articles = saveArticleRepository.findAllByTbUserId("test123");
        assertThat(articles.size()).isEqualTo(3);
    }

    @Test
    void existsByTbUserIDAndArticleId_test(){
        saveArticleRepository.save(saveArticle1);
        Boolean result1 = saveArticleRepository.existsByTbUserIdAndTbArticleId("test123", 1);
        Boolean result2 = saveArticleRepository.existsByTbUserIdAndTbArticleId("test123",2);
        assertTrue(result1);
        assertFalse(result2);
    }

}