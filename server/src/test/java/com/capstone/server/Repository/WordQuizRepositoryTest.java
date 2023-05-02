package com.capstone.server.Repository;

import com.capstone.server.Domain.WordQuiz;
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
class WordQuizRepositoryTest {

    @Autowired
    WordQuizRepository wordQuizRepository;
    static WordQuiz wordQuiz1;
    static WordQuiz wordQuiz2;
    static WordQuiz wordQuiz3;
    @BeforeAll
    static void setup(){
        wordQuiz1 = new WordQuiz(1, "사흘의 뜻을 고르세요", 1);
        wordQuiz2 = new WordQuiz(2, "일석이조의 뜻을 고르세요", 2);
        wordQuiz3 = new WordQuiz(3, "금일의 뜻을 고르세요", 1);
    }
    @Test
    void findTop3By_test(){
        wordQuizRepository.save(wordQuiz1);
        wordQuizRepository.save(wordQuiz2);
        wordQuizRepository.save(wordQuiz3);
        List<WordQuiz> result = wordQuizRepository.findTop3By();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getQuizNo()).isEqualTo(1);
    }
}