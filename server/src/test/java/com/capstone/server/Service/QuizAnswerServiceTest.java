package com.capstone.server.Service;

import com.capstone.server.Repository.QuizAnswerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class QuizAnswerServiceTest {
    @Mock
    QuizAnswerRepository quizAnswerRepository;
    @InjectMocks
    QuizAnswerService quizAnswerService;

    @Test
    void getUserQuizInfo_test(){
        when(quizAnswerRepository.countAllByCorrectFlIsAndTbUserIdJoinArticle(anyString(), eq("D1")))
                .thenReturn(3);

        when(quizAnswerRepository.countAllByCorrectFlIsAndTbUserIdJoinArticle(anyString(), eq("D2")))
                .thenReturn(2);
        int D1_result = quizAnswerService.getUserQuizInfo("test1234", "D1");
        int D2_result = quizAnswerService.getUserQuizInfo("test1234", "D2");
        assertThat(D1_result).isEqualTo(3);
        assertThat(D2_result).isEqualTo(2);
    }

}