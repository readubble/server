package com.capstone.server.Repository;

import com.capstone.server.Domain.Article;
import com.capstone.server.Domain.QuizAnswer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class QuizAnswerRepositoryTest {
    @Autowired
    QuizAnswerRepository quizAnswerRepository;
    @Autowired
    ArticleRepository articleRepository;

    static QuizAnswer quizAnswer1;
    static QuizAnswer quizAnswer2;
    static QuizAnswer quizAnswer3;
    static QuizAnswer quizAnswer4;
    static QuizAnswer quizAnswer5;
    static Article article1;
    static Article article2;


    @BeforeAll
    static void setup(){
        quizAnswer1 = QuizAnswer.builder()
                .tbUserId("1234")
                .correctFl("True")
                .tbArticleId(1)
                .tbQuizNo(1)
                .quizInp(1)
                .correctFl("Y").build();
        quizAnswer2 = QuizAnswer.builder()
                .tbUserId("1234")
                .correctFl("True")
                .tbArticleId(1)
                .tbQuizNo(2)
                .quizInp(1)
                .correctFl("Y").build();
        quizAnswer3 = QuizAnswer.builder()
                .tbUserId("1234")
                .correctFl("True")
                .tbArticleId(1)
                .tbQuizNo(3)
                .quizInp(1)
                .correctFl("N").build();
        quizAnswer4 = QuizAnswer.builder()
                .tbUserId("1234")
                .correctFl("True")
                .tbArticleId(2)
                .tbQuizNo(1)
                .quizInp(1)
                .correctFl("Y").build();
        quizAnswer5 = QuizAnswer.builder()
                .tbUserId("1234")
                .correctFl("True")
                .tbArticleId(2)
                .tbQuizNo(2)
                .quizInp(1)
                .correctFl("N").build();
        article1 = Article.builder()
                .id(1)
                .difficulty("1").build();
        article2 = Article.builder()
                .id(2).difficulty("2").build();

    }

    @Test
    void countByCorrectFlIsAndTbUserId_Y_test(){
        quizAnswerRepository.save(quizAnswer1);
        quizAnswerRepository.save(quizAnswer2);
        quizAnswerRepository.save(quizAnswer3);
        quizAnswerRepository.save(quizAnswer4);
        quizAnswerRepository.save(quizAnswer5);

        int result = quizAnswerRepository.countAllByCorrectFlIsAndTbUserId("Y", "1234");

        assertThat(result).isEqualTo(3);
    }

    @Test
    void countAllByCorrectFlIsAndTbUserIdJoinArticle_Y_test(){
        quizAnswerRepository.save(quizAnswer1);
        quizAnswerRepository.save(quizAnswer2);
        quizAnswerRepository.save(quizAnswer3);
        quizAnswerRepository.save(quizAnswer4);
        quizAnswerRepository.save(quizAnswer5);
        articleRepository.save(article1);
        articleRepository.save(article2);

        int result = quizAnswerRepository.countAllByCorrectFlIsAndTbUserIdJoinArticle("Y", "1");
        assertThat(result).isEqualTo(2);
    }

}