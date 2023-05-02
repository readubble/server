package com.capstone.server.Repository;

import com.capstone.server.Domain.WordQuizItem;
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
class WordQuizItemRepositoryTest {
    @Autowired
    WordQuizItemRepository wordQuizItemRepository;
    static WordQuizItem wordQuizItem1_1;
    static WordQuizItem wordQuizItem1_2;
    static WordQuizItem wordQuizItem2_1;
    static WordQuizItem wordQuizItem2_2;
    static WordQuizItem wordQuizItem3_1;
    static WordQuizItem wordQuizItem3_2;
    @BeforeAll
    static void setup(){
        wordQuizItem1_1 = new WordQuizItem(1, 1, "3일");
        wordQuizItem1_2 = new WordQuizItem(1,2,"4일");
        wordQuizItem2_1 = new WordQuizItem(2,1,"하나를 얻고 하나를 잃는다");
        wordQuizItem2_2 = new WordQuizItem(2,2,"하나를 얻고 하나를 더 얻는다");
        wordQuizItem3_1 = new WordQuizItem(3,1,"오늘");
        wordQuizItem3_2 = new WordQuizItem(3,2, "금요일");
    }
    @Test
    void findAllByTbWordQuizQuizNo_test(){
        wordQuizItemRepository.save(wordQuizItem1_1);
        wordQuizItemRepository.save(wordQuizItem1_2);
        wordQuizItemRepository.save(wordQuizItem2_1);
        wordQuizItemRepository.save(wordQuizItem2_2);
        wordQuizItemRepository.save(wordQuizItem3_1);
        wordQuizItemRepository.save(wordQuizItem3_2);

        List<WordQuizItem> result_1 = wordQuizItemRepository.findAllByTbWordQuizQuizNo(1);
        assertThat(result_1.size()).isEqualTo(2);
        assertThat(result_1.get(0).getTbWordQuizQuizNo()).isEqualTo(1);
        assertThat(result_1.get(0).getItemValue()).isEqualTo("3일");
    }

}