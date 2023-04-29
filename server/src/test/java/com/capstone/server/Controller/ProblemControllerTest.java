package com.capstone.server.Controller;

import com.capstone.server.Domain.User;
import com.capstone.server.Interface.ArticleInterface;
import com.capstone.server.Repository.UserRepository;
import com.capstone.server.Service.ArticleService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ProblemControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ArticleService articleService;
    @Autowired
    UserRepository userRepository;
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
    void problemList_test() throws Exception{
        List<ArticleInterface> result = new ArrayList<>();

        ArticleInterface article = new ArticleInterface() {
            @Override
            public String getAtcTitle() {
                return "title1";
            }

            @Override
            public String getAtcWriter() {
                return "writer";
            }

            @Override
            public String getAtcPhotoIn() {
                return "http://url.com";
            }

            @Override
            public String getDifficulty() {
                return "D1";
            }

            @Override
            public String getGenre() {
                return "art";
            }
        };
        result.add(article);

        when(articleService.articleList(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(result);

        mvc.perform(get("/problem/test123?category=art")
                        .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].atcTitle").value("title1"));
    }

}