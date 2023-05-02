package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.WordQuizRequestDTO;
import com.capstone.server.Domain.WordQuizAnswer;
import com.capstone.server.Repository.WordQuizAnswerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class WordQuizAnswerServiceTest {
    @Mock
    WordQuizAnswerRepository wordQuizAnswerRepository;
    @InjectMocks
    WordQuizAnswerService wordQuizAnswerService;

    @Test
    void wordQuizAnswer_test(){
        wordQuizAnswerService.wordQuizSave(WordQuizRequestDTO.builder()
                .quizId(1)
                .userId("test123")
                .quizChoice(1)
                .quizResult("Y").build());
        verify(wordQuizAnswerRepository, times(1)).save(any(WordQuizAnswer.class));
    }

}