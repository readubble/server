package com.capstone.server.Repository;

import com.capstone.server.Domain.WordQuizAnswer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class WordQuizAnswerRepositoryTest {
    @Autowired
    WordQuizAnswerRepository wordQuizAnswerRepository;

    static WordQuizAnswer wordQuizAnswer1;
    static WordQuizAnswer wordQuizAnswer3;
    @BeforeAll
    static void setup(){
        wordQuizAnswer1 = new WordQuizAnswer("test123", 1, 1, "Y");
        wordQuizAnswer3 = new WordQuizAnswer("test1234", 3, 2, "N");
    }
    @Test
    void findByTbUserIdAndTbWordQuizQuizNo_test(){
        wordQuizAnswerRepository.save(wordQuizAnswer1);
        wordQuizAnswerRepository.save(wordQuizAnswer3);
        Optional<WordQuizAnswer> result_1 = wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizQuizNo("test123", 1);
        Optional<WordQuizAnswer> result_2 = wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizQuizNo("test123", 2);
        assertThat(result_1.isPresent()).isTrue();
        assertThat(result_1.get().getTbWordQuizQuizNo()).isEqualTo(1);
        assertThat(result_2.isEmpty()).isTrue();
    }

}