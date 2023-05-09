package com.capstone.server.Service;

import com.capstone.server.DTO.ResponseDTO.WordQuizResultDTO;
import com.capstone.server.Domain.WordQuiz;
import com.capstone.server.Domain.WordQuizAnswer;
import com.capstone.server.Domain.WordQuizItem;
import com.capstone.server.Repository.WordQuizAnswerRepository;
import com.capstone.server.Repository.WordQuizItemRepository;
import com.capstone.server.Repository.WordQuizRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class WordQuizServiceTest {
    @Mock
    WordQuizRepository wordQuizRepository;
    @Mock
    WordQuizItemRepository wordQuizItemRepository;
    @Mock
    WordQuizAnswerRepository wordQuizAnswerRepository;
    @InjectMocks
    WordQuizService wordQuizService;

    static WordQuiz wordQuiz1;
    static WordQuiz wordQuiz2;
    static WordQuiz wordQuiz3;
    static WordQuizItem wordQuizItem1_1;
    static WordQuizItem wordQuizItem1_2;
    static WordQuizItem wordQuizItem2_1;
    static WordQuizItem wordQuizItem2_2;
    static WordQuizItem wordQuizItem3_1;
    static WordQuizItem wordQuizItem3_2;
    static WordQuizAnswer wordQuizAnswer1;
    static WordQuizAnswer wordQuizAnswer3;
    @BeforeAll
    static void setup(){
        wordQuiz1 = new WordQuiz(1, "사흘의 뜻을 고르세요", 1);
        wordQuiz2 = new WordQuiz(2, "일석이조의 뜻을 고르세요", 2);
        wordQuiz3 = new WordQuiz(3, "금일의 뜻을 고르세요", 1);
        wordQuizItem1_1 = new WordQuizItem(1, 1, "3일");
        wordQuizItem1_2 = new WordQuizItem(1,2,"4일");
        wordQuizItem2_1 = new WordQuizItem(2,1,"하나를 얻고 하나를 잃는다");
        wordQuizItem2_2 = new WordQuizItem(2,2,"하나를 얻고 하나를 더 얻는다");
        wordQuizItem3_1 = new WordQuizItem(3,1,"오늘");
        wordQuizItem3_2 = new WordQuizItem(3,2, "금요일");
        wordQuizAnswer1 = new WordQuizAnswer("test123", 1, 1, "Y");
        wordQuizAnswer3 = new WordQuizAnswer("test123", 3, 2, "N");
    }

    @Test
    void wordQuizList_test(){
        List<WordQuiz> wqr_result = new ArrayList<>();
        wqr_result.add(wordQuiz1); wqr_result.add(wordQuiz2); wqr_result.add(wordQuiz3);
        when(wordQuizRepository.findTop3By()).thenReturn(wqr_result);
        List<WordQuizItem> wqa1_result = new ArrayList<>();
        wqa1_result.add(wordQuizItem1_1); wqa1_result.add(wordQuizItem1_2);
        List<WordQuizItem> wqa2_result = new ArrayList<>();
        wqa1_result.add(wordQuizItem2_1); wqa1_result.add(wordQuizItem2_2);
        List<WordQuizItem> wqa3_result = new ArrayList<>();
        wqa1_result.add(wordQuizItem3_1); wqa1_result.add(wordQuizItem3_2);
        when(wordQuizItemRepository.findAllByWordQuizNo(1)).thenReturn(wqa1_result);
        when(wordQuizItemRepository.findAllByWordQuizNo(2)).thenReturn(wqa2_result);
        when(wordQuizItemRepository.findAllByWordQuizNo(3)).thenReturn(wqa3_result);
        when(wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizNo("test123", 1)).thenReturn(Optional.of(wordQuizAnswer1));
        when(wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizNo("test123", 2)).thenReturn(Optional.empty());
        when(wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizNo("test123",3)).thenReturn(Optional.of(wordQuizAnswer3));

        List<WordQuizResultDTO> result = wordQuizService.wordQuizList("test123");

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getSolved()).isEqualTo("Y");
        assertThat(result.get(0).getSolvedChoice()).isEqualTo(1);
        assertThat(result.get(1).getSolved()).isEqualTo("N");
        assertThat(result.get(2).getSolvedChoice()).isEqualTo(2);
        assertThat(result.get(0).getQuiz()).isEqualTo(wordQuiz1.getQuizQuestion());
    }

}