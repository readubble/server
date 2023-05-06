package com.capstone.server.Service;

import com.capstone.server.Domain.Quiz;
import com.capstone.server.Domain.QuizItem;
import com.capstone.server.Repository.QuizItemRepository;
import com.capstone.server.Repository.QuizRepository;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class QuizServiceTest {
    @Mock
    QuizItemRepository quizItemRepository;
    @Mock
    QuizRepository quizRepository;
    @InjectMocks
    QuizService quizService;

    @Test
    public void quiz_test(){
        when(quizRepository.findAllByTbArticleId(1))
                .thenReturn(List.of(Quiz.builder()
                        .tbArticleId(1)
                        .quizNo(1)
                        .quizAns(1)
                        .quizQuestion("문제").build(),
                        Quiz.builder()
                                .tbArticleId(1)
                                .quizNo(2)
                                .quizAns(1)
                                .quizQuestion("문제").build(),
                        Quiz.builder()
                                .tbArticleId(1)
                                .quizNo(3)
                                .quizAns(1)
                                .quizQuestion("문제").build()));
        when(quizItemRepository.findAllByTbQuizQuizNo(1))
                .thenReturn(List.of(QuizItem.builder()
                        .tbQuizQuizNo(1)
                        .itemValue("1-항목1")
                        .itemNo(1)
                        .tbQuizTbArticleId(1).build(),
                        QuizItem.builder()
                                .tbQuizQuizNo(1)
                                .itemValue("1-항목2")
                                .itemNo(2)
                                .tbQuizTbArticleId(1).build(),
                        QuizItem.builder()
                                        .tbQuizQuizNo(1)
                                        .itemValue("1-항목3")
                                        .itemNo(3)
                                        .tbQuizTbArticleId(1).build()));
        when(quizItemRepository.findAllByTbQuizQuizNo(2))
                .thenReturn(List.of(QuizItem.builder()
                                .tbQuizQuizNo(2)
                                .itemValue("2-항목1")
                                .itemNo(1)
                                .tbQuizTbArticleId(1).build(),
                        QuizItem.builder()
                                        .tbQuizQuizNo(2)
                                        .itemValue("2-항목2")
                                        .itemNo(2)
                                        .tbQuizTbArticleId(1).build(),
                        QuizItem.builder()
                                        .tbQuizQuizNo(2)
                                        .itemValue("2-항목3")
                                        .itemNo(3)
                                        .tbQuizTbArticleId(1).build()));
        when(quizItemRepository.findAllByTbQuizQuizNo(3))
                .thenReturn(List.of(QuizItem.builder()
                                .tbQuizQuizNo(3)
                                .itemValue("3-항목1")
                                .itemNo(1)
                                .tbQuizTbArticleId(1).build(),
                        QuizItem.builder()
                                        .tbQuizQuizNo(3)
                                        .itemValue("3-항목2")
                                        .itemNo(2)
                                        .tbQuizTbArticleId(1).build(),
                        QuizItem.builder()
                                        .tbQuizQuizNo(1)
                                        .itemValue("3-항목3")
                                        .itemNo(3)
                                        .tbQuizTbArticleId(1).build()));

        List<JSONObject> result = quizService.Quiz(1);
        assertThat(result.get(0).get("choices")).isEqualTo(
                List.of("1-항목1", "1-항목2", "1-항목3")
        );
    }


}