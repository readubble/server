package com.capstone.server.Repository;

import com.capstone.server.Domain.Quiz;
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
class QuizRepositoryTest {
    @Autowired
    QuizRepository quizRepository;

    static Quiz quiz1;
    static Quiz quiz2;

    @BeforeAll
    static void setup(){
        quiz1 = Quiz.builder()
                .tbArticleId(1)
                .quizNo(1)
                .quizAns(1)
                .quizQuestion("")
                .build();
        quiz2 = Quiz.builder()
                .tbArticleId(1)
                .quizNo(2)
                .quizAns(1)
                .quizQuestion("")
                .build();
    }

    @Test
    void findAllByTbArticleId_test(){
        quizRepository.save(quiz1);
        quizRepository.save(quiz2);
        List<Quiz> result = quizRepository.findAllByTbArticleId(1);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getQuizNo()).isEqualTo(1);
    }

}