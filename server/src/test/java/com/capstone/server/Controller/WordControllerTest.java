package com.capstone.server.Controller;

import com.capstone.server.DTO.ResponseDTO.DictResponseDTO;
import com.capstone.server.Domain.SaveWord;
import com.capstone.server.Domain.User;
import com.capstone.server.Repository.UserRepository;
import com.capstone.server.Service.DictService;
import com.capstone.server.Service.SaveWordService;
import com.capstone.server.Service.SearchService;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class WordControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SearchService searchService;
    @MockBean
    SaveWordService saveWordService;

    @MockBean
    DictService dictService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    static DictResponseDTO dict1;
    static DictResponseDTO dict2;

    @PostConstruct
    void setting() {
        userRepository.save(User.builder()
                .id("test123")
                .userNm("tester")
                .userPw(bCryptPasswordEncoder.encode("1234"))
                .joinDt(new Date())
                .userLevel("L1")
                .userExp(0)
                .userPhotoIn("")
                .role("ROLE_USER").build());
        dict1 = new DictResponseDTO(1, "사과", "살이 연하고 달며 물이 많은 참외", "Y");
        dict2 = new DictResponseDTO(2, "사과", "조선 시대에, 오위에 둔 정육품의 군직. 현직에 종사하고 있찌 않은 문관, 무관 및 음관이 맡았다.", "N");

    }

    @Test
    @WithUserDetails("test123")
    void wordSearchTest() throws Exception {
        when(dictService.getDictionaryDB("사과", "test123")).thenReturn(List.of(dict1, dict2));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "test123");
        jsonObject.put("keyword", "사과");
        String content = objectMapper.writeValueAsString(jsonObject);

        mvc.perform(post("/word")
                        .header("Authorization", "token")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].wordNo").value(1));
    }

    @Test
    @WithUserDetails("test123")
    public void wordBookmarkTest() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "test123");
        String content = objectMapper.writeValueAsString(jsonObject);
        mvc.perform(post("/word/1/bookmark")
                        .header("Authorization", "")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails("test123")
    public void wordBookmarkListTest() throws Exception{
        SaveWord saveWord = new SaveWord(1,"test123",123,"","");
        when(saveWordService.getBookmarkWords("test123"))
                .thenReturn(List.of(saveWord));
        mvc.perform(get("/word/bookmark/users/test123")
                        .header("Authorization",""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].targetCode").value(123));

    }
}