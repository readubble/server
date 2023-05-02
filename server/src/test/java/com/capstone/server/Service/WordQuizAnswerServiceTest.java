package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.WordQuizRequestDTO;
import com.capstone.server.Domain.WordQuiz;
import com.capstone.server.Domain.WordQuizAnswer;
import com.capstone.server.Repository.WordQuizAnswerRepository;
import com.capstone.server.Repository.WordQuizRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class WordQuizAnswerServiceTest {
    @Mock
    WordQuizAnswerRepository wordQuizAnswerRepository;
    @Mock
    WordQuizRepository wordQuizRepository;
    @InjectMocks
    WordQuizAnswerService wordQuizAnswerService;

    static WordQuiz wordQuiz1;
    static WordQuiz wordQuiz2;
    static WordQuiz wordQuiz3;
    static WordQuizAnswer wordQuizAnswer1;
    static WordQuizAnswer wordQuizAnswer3;
    @BeforeAll
    static void setup(){

        wordQuiz1 = new WordQuiz(1, "사흘의 뜻을 고르세요", 1);
        wordQuiz2 = new WordQuiz(2, "일석이조의 뜻을 고르세요", 2);
        wordQuiz3 = new WordQuiz(3, "금일의 뜻을 고르세요", 1);
        wordQuizAnswer1 = new WordQuizAnswer("test1234", 1, 1, "Y");
        wordQuizAnswer3 = new WordQuizAnswer("test1234", 3, 2, "N");
    }
    @Test
    void wordQuizAnswer_test(){
        wordQuizAnswerService.wordQuizSave(WordQuizRequestDTO.builder()
                .quizId(1)
                .userId("test123")
                .quizChoice(1)
                .quizResult("Y").build());
        verify(wordQuizAnswerRepository, times(1)).save(any(WordQuizAnswer.class));
    }

    @Test
    void wordQuizInfo_test(){
        when(wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizQuizNo("test1234", 1))
                .thenReturn(Optional.of(wordQuizAnswer1));
        when(wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizQuizNo("test1234",2))
                .thenReturn(Optional.empty());
        when(wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizQuizNo("test1234",3))
                .thenReturn(Optional.of(wordQuizAnswer3));
        when(wordQuizRepository.findTop3By())
                .thenReturn(List.of(wordQuiz1, wordQuiz2, wordQuiz3));
        String result = wordQuizAnswerService.wordQuizInfo("test1234");
        assertThat(result).isEqualTo("TNF");
    }

}