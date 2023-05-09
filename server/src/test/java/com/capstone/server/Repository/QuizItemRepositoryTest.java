package com.capstone.server.Repository;

import com.capstone.server.Domain.QuizItem;
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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class QuizItemRepositoryTest {
    @Autowired
    QuizItemRepository quizItemRepository;

    static QuizItem quizItem1;
    static QuizItem quizItem2;

    @BeforeAll
    static void setup(){
        quizItem1 = QuizItem.builder()
                .tbArticleId(1)
                .tbQuizNo(1)
                .itemValue("항목1")
                .itemNo(1)
                .build();
        quizItem2 = QuizItem.builder()
                .tbArticleId(1)
                .tbQuizNo(1)
                .itemValue("항목2")
                .itemNo(2)
                .build();

    }

    @Test
    void findAllByTbQuizNo_test(){
        quizItemRepository.save(quizItem1);
        quizItemRepository.save(quizItem2);
        List<QuizItem> result = quizItemRepository.findAllByTbQuizNo(1);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getItemValue()).isEqualTo("항목1");
    }

}