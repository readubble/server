package com.capstone.server.Controller;

import com.capstone.server.DTO.RequestDTO.JoinRequestDTO;
import com.capstone.server.DTO.UserDTO;
import com.capstone.server.Domain.User;
import com.capstone.server.Interface.TbReadInterface;
import com.capstone.server.Repository.UserRepository;
import com.capstone.server.Service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;
    @MockBean
    TokenService tokenService;
    @MockBean
    QuizAnswerService quizAnswerService;
    @MockBean
    TbReadService tbReadService;
    @MockBean
    WordQuizAnswerService wordQuizAnswerService;
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
    void join_test() throws Exception{
        String content = objectMapper.writeValueAsString((JoinRequestDTO.builder()
                .id("test12345")
                .nickname("tester2")
                .password("1234")
                .role("ROLE_USER").build()));
        mvc.perform(post("/users")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).join(argThat(joinRequestDTO->joinRequestDTO.getId().equals("test12345")));
    }

    @Test
    @WithUserDetails("test123")
    void auto_login_test() throws Exception{
        when(tokenService.getUsername(anyString())).thenReturn("test123");
        mvc.perform(get("/users/authorize/auto")
                        .header("Authorization", "token"))
                .andExpect(status().isOk());
        verify(userService, times(1)).autoLogin(argThat(String->String.equals("test123")));
    }

    @Test
    @WithUserDetails("test123")
    void token_renew_test() throws Exception{
        when(tokenService.getUsername(anyString())).thenReturn("test123");
        when(tokenService.isTokenAvailable(anyString())).thenReturn(true);
        when(tokenService.renewToken(anyString(), anyString())).thenReturn("");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refresh_token", "");
        String content = objectMapper.writeValueAsString((jsonObject));
        mvc.perform(post("/users/authorize/token")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.access_token").value(""));
    }

    @Test
    @WithUserDetails("test123")
    void logout_test() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "test123");
        String content = objectMapper.writeValueAsString(jsonObject);
        mvc.perform(post("/users/logout")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "token"))
            .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("test123")
    void userInfo_test() throws Exception{
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        when(userService.getUserInfo(anyString()))
                .thenReturn(UserDTO.builder()
                        .id("test123")
                        .userNm("tester")
                        .userPw(bCryptPasswordEncoder.encode("1234"))
                        .joinDt(date)
                        .userLevel("L1")
                        .userExp(0)
                        .userPhotoIn("")
                        .role("ROLE_USER").build());
        mvc.perform(get("/users/test123")
                .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.date").value(simpleDateFormat.format(date)));
    }

    @Test
    @WithUserDetails("test123")
    void userStatisticsInfo_test() throws Exception{
        when(quizAnswerService.getUserQuizAnswer(anyString(), eq("D1")))
                .thenReturn(3);
        when(quizAnswerService.getUserQuizAnswer(anyString(), eq("D2")))
                .thenReturn(1);
        when(quizAnswerService.getUserQuizAnswer(anyString(), eq("D3")))
                .thenReturn(0);
        mvc.perform(get("/users/test123/statistics")
                .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].num").value(3))
                .andExpect(jsonPath("$.data[1].num").value(1))
                .andExpect(jsonPath("$.data[2].num").value(0));
    }

    @Test
    @WithUserDetails("test123")
    void userProblemInfo_test() throws Exception{
        List list = new ArrayList();
        TbReadInterface tbReadInterface = new TbReadInterface() {
            @Override
            public Integer getTbArticleId() {
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

        when(tbReadService.getUserReadHistory(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(list);

        mvc.perform(get("/users/test123/problem?level=D1")
                .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].atcTitle").value("title1"));


    }

    @Test
    @WithUserDetails("test123")
    void userQuizInformation_test() throws Exception {
        when(wordQuizAnswerService.getWordQuiz("test123")).thenReturn("TNF");
        mvc.perform(get("/users/test123/quiz")
                        .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value("TNF"));
    }

    @Test
    @WithUserDetails("test123")
    void profile_test() throws Exception{
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "44036562.png", "image/png", new FileInputStream("src/main/resources/tmp/44036562.png")
        );
        when(userService.uploadFile("test123", multipartFile)).thenReturn("http://url.com");

        mvc.perform(
                multipart("/users/test123/profile")
                        .file(multipartFile)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.url").isNotEmpty());

    }

}