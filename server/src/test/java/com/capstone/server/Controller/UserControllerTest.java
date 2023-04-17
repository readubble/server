package com.capstone.server.Controller;

import com.capstone.server.DTO.RequestDTO.JoinRequestDTO;
import com.capstone.server.Domain.User;
import com.capstone.server.Repository.UserRepository;
import com.capstone.server.Service.TokenService;
import com.capstone.server.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    void setting(){
        userRepository.save(User.builder()
                .id("test123")
                .email("test@tester.com")
                .nickname("tester")
                .password(bCryptPasswordEncoder.encode("1234"))
                .role("ROLE_USER").build());
    }
    @Test
    void join_test() throws Exception{
        String content = objectMapper.writeValueAsString((JoinRequestDTO.builder()
                .id("test12345")
                .email("test1@tester.com")
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
        verify(userService, times(1)).AutoLogin(argThat(String->String.equals("test123")));
    }

    @Test
    @WithUserDetails("test123")
    void token_renew_test() throws Exception{
        when(tokenService.getUsername(anyString())).thenReturn("test123");
        when(tokenService.TokenAvailableCheck(anyString())).thenReturn(true);
        when(tokenService.TokenRenew(anyString(), anyString())).thenReturn("");

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

}