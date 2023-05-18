package com.capstone.server.Repository;

import com.capstone.server.Domain.SaveWord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class SaveWordRepositoryTest {

    @Autowired
    private SaveWordRepository saveWordRepository;

    static SaveWord saveWord1;
    static SaveWord saveWord2;
    static SaveWord saveWord3;

    @BeforeAll
    static void setup() {
        saveWord1 = new SaveWord(1, "test123", 123, "사과");
        saveWord2 = new SaveWord(2, "test123", 456, "배");
        saveWord3 = new SaveWord(3, "test123", 789, "오렌지");
    }

    @BeforeEach
    void saveWords() {
        saveWordRepository.save(saveWord1);
        saveWordRepository.save(saveWord2);
        saveWordRepository.save(saveWord3);
    }

    @Test
    void findByTbUserIdAndTargetCode() {
        SaveWord result = saveWordRepository.findByTbUserIdAndTargetCode("test123", 123).get();
        assertThat(result.getTargetCode()).isEqualTo(123);
    }

    @Test
    void findAllByTbUserId() {
        List<SaveWord> result = saveWordRepository.findAllByTbUserId("test123");
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void deleteTest(){
        boolean result = saveWordRepository.existsByTbUserIdAndTargetCode(saveWord1.getTbUserId(), saveWord1.getTargetCode());
        assertTrue(result);
        saveWordRepository.delete(saveWord1);
        result = saveWordRepository.existsByTbUserIdAndTargetCode(saveWord1.getTbUserId(), saveWord1.getTargetCode());
        assertFalse(result);
    }

    @Test
    void existsByTbUserIdAndTargetCode() {
        boolean result = saveWordRepository.existsByTbUserIdAndTargetCode("test123", 456);
        assertThat(result).isTrue();
    }

}
