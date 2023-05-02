package com.capstone.server.Controller;

import com.capstone.server.DTO.ResponseDTO.WordQuizResultDTO;
import com.capstone.server.Domain.User;
import com.capstone.server.Repository.UserRepository;
import com.capstone.server.Service.WordQuizService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class QuizControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @MockBean
    WordQuizService wordQuizService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    void setting(){
        userRepository.save(User.builder()
                .id("test123")
                .userNm("tester")
                .userPw(bCryptPasswordEncoder.encode("1234"))
                .joinDt(new Date())
                .userLevel("L1")
                .userExp(0)
                .userPhotoIn("")
                .role("ROLE_USER").build());
    }
    @Test
    @WithUserDetails("test123")
    void wordQuizList_test() throws Exception {
        WordQuizResultDTO wordQuizResultDTO1 = new WordQuizResultDTO(1, "사흘의 뜻을 고르세요", List.of("3일", "4일"), 1, "Y", 1);
        WordQuizResultDTO wordQuizResultDTO2 = new WordQuizResultDTO(2, "일석이조의 뜻을 고르세요", List.of("하나를 얻고 하나를 잃는다", "하나를 얻고 또 하나를 얻는다"), 2, "N", -1);
        WordQuizResultDTO wordQuizResultDTO3 = new WordQuizResultDTO(3, "금일의 뜻을 고르세요", List.of("오늘","금요일"), 1, "Y", 2);
        when(wordQuizService.wordQuizList("test123"))
                .thenReturn(List.of(wordQuizResultDTO1, wordQuizResultDTO2, wordQuizResultDTO3));
        mvc.perform(get("/quiz/test123")
                        .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].solved").value("Y"));

    }

}