package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.ProblemRequestDTO;
import com.capstone.server.Domain.QuizAnswer;
import com.capstone.server.Domain.TbRead;
import com.capstone.server.Interface.TbReadInterface;
import com.capstone.server.Repository.QuizAnswerRepository;
import com.capstone.server.Repository.TbReadRepository;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class TbReadServiceTest {
    @Mock
    TbReadRepository tbReadRepository;
    @Mock
    QuizAnswerRepository quizAnswerRepository;
    @Mock
    ArticleService articleService;
    @InjectMocks
    TbReadService tbReadService;

    @Test
    void getUserReadInfo_test(){
        List list = new ArrayList();
        TbReadInterface tbReadInterface = new TbReadInterface() {
            @Override
            public int getTbArticleId() {
                return 1;
            }

            @Override
            public String getAtcTitle() {
                return "title1";
            }

            @Override
            public String getGenre() {
                return "01";
            }
        };
        list.add(tbReadInterface);
        Page<TbReadInterface> page = new PageImpl<>(list);
        when(tbReadRepository.findAllByTbUserIdLEFTJOINArticle(anyString(), anyString(), any()))
                .thenReturn(page);

        List<TbReadInterface> result = tbReadService.getUserReadInfo("test1234", "D1", 0, 1);

        assertThat(result.get(0).getTbArticleId()).isEqualTo(1);

    }

    @Test
    void save_test(){
        tbReadService.save(ProblemRequestDTO
                .builder()
                .userId("test123")
                .keyword(List.of("1","2","3"))
                .sentence("문장|문장|문장")
                .startTime(new Time(20000))
                .finishTime(new Time(30000))
                .totalTime(new Time(10000))
                .quizId(List.of(1,2,3))
                .quizChoice(List.of(1,2,3))
                .quizResult(List.of("Y","Y","Y")).build(), 1);
        verify(tbReadRepository, times(1)).save(any(TbRead.class));
        verify(quizAnswerRepository,times(3)).save(any(QuizAnswer.class));
    }

    @Test
    void getResult_test(){
        when(tbReadRepository.findByTbUserIdAndTbArticleId("test123", 1))
                .thenReturn(TbRead.builder()
                        .tbUserId("test123")
                        .tbArticleId(1)
                        .inpKwd1("1")
                        .inpKwd2("2")
                        .inpKwd3("3")
                        .inpTopic("문항1|문항2|문항3")
                        .inpSmr("요약결과")
                        .saveFl("N")
                        .solveFl("Y")
                        .finishTime(new Time(30000))
                        .startTime(new Time(10000))
                        .totalTime(new Time(20000))
                        .build());
        when(articleService.getSummarization(1))
                .thenReturn("ai요약결과");
        JSONObject jsonObject = tbReadService.getResult(1, "test123");
        assertThat(jsonObject.get("sentence")).isEqualTo(List.of("문항1", "문항2", "문항3"));
        assertThat(jsonObject.get("ai-summarization")).isEqualTo("ai요약결과");

    }

}