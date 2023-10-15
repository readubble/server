package com.capstone.server.Controller;

import com.capstone.server.DTO.ArticleDTO;
import com.capstone.server.DTO.RequestDTO.ProblemRequestDTO;
import com.capstone.server.Domain.User;
import com.capstone.server.Interface.ArticleInterface;
import com.capstone.server.Repository.UserRepository;
import com.capstone.server.Service.ArticleService;
import com.capstone.server.Service.QuizService;
import com.capstone.server.Service.SaveArticleService;
import com.capstone.server.Service.TbReadService;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @MockBean
    QuizService quizService;
    @MockBean
    TbReadService tbReadService;
    @MockBean
    SaveArticleService saveArticleService;
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
            public Integer getId(){return 1;}
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

        when(articleService.getArticles(anyString(), anyInt(), anyInt(), anyInt()))
                .thenReturn(result);

        mvc.perform(get("/problem/users/test123?category=art")
                        .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].atcTitle").value("title1"));
    }

    @Test
    @WithUserDetails("test123")
    public void problem_test() throws Exception {
        JSONObject problem = new JSONObject();
        problem.put("title", "문제");
        problem.put("content", List.of(
                List.of("“거인이 내 뒤로 뚜벅뚜벅 쫓아오는 소리를 항상 들어야 한다고 생각해보게. 그때 그 기분을 자네는 전혀 상상할 수 없을 걸세.” - 요하네스 브람스"),
                List.of("19세기의 다른 교향곡 작곡가들과 마찬가지로 브람스 역시 베토벤이라는 거인을 피해갈 수 없었다.", " 광대한 우주의 소리를 담아낸 베토벤의 교향곡이야말로 독일 교향곡의 모범답안으로 여겨지던 당대의 분위기에선 신작 교향곡이 나오면 곧바로 베토벤과 비교될 수밖에 없었다.", " 그러니 브람스가 그의 첫 번째 교향곡을 완성하기까지 무려 20여 년의 세월을 투자했던 것도 무리가 아니다."),
                List.of("브람스의 첫 번째 교향곡은 유난히 베토벤의 교향곡을 닮았다.", " 이 곡에서 팀파니는 마치 베토벤의 [교향곡 제5번]의 ‘운명’의 동기를 닮은 리듬을 집요하게 반복한다.", " 그 때문에 당대의 뛰어난 피아니스트이나 지휘자이며 음악평론가인 한스 폰 뷜로는 브람스의 [교향곡 제1번]을 가리켜 ‘베토벤의 제10번’이라 불렀다.", " 이후 브람스는 교향곡 두 곡을 더 작곡했는데, 그 중 [교향곡 제2번]은 ‘브람스의 전원’, [제3번]은 ‘브람스의 영웅’에 비유되면서 여전히 베토벤의 교향곡과 유사하다는 혐의에서 벗어나지 못했다.", " 하지만 [교향곡 제4번]은 진정한 브람스만의 음악이며 아무도 이 교향곡을 베토벤의 작품에 빗대지 않았다."),
                List.of("1885년, 이미 세 곡의 훌륭한 교향곡을 통해 교향곡 작곡가로서의 능력을 입증해낸 브람스는 이제 인생의 말년에 접어들어 자신만의 음악적 깊이를 교향곡에 담아내고자 그의 마지막 교향곡의 작곡에 심혈을 기울였다.", " 이 곡은 브람스의 단조 교향곡들 가운데 유일하게 피날레에서 장조의 환희로 변하지 않고 단조의 우울함을 그대로 간직하고 있다.", " 이로써 브람스는 ‘어둠에서 광명으로’ 향하는 베토벤 풍의 구도를 버리고 어둠으로부터 비극으로 침잠해 가는 자신만의 교향곡 모델을 확립하게 된 것이다."),
                List.of("브람스의 [교향곡 4번]은 1885년 10월 25일에 마이닝겐에서 작곡가 자신의 지휘로 초연되었다.", " 초연 후 11년이 지난 1896년, 브람스는 교향곡 제4번의 악보를 펼치고 1악장의 첫 4음인 B-G-E-C 위에 “오!죽음이여, 오 죽음이여!”라고 적었다."," 그리고 이듬해, 브람스는 영원한 안식을 찾았다.")
        ));
        problem.put("level", "하");
        problem.put("author", "클래식 명곡 명연주, 최은규");
        when(articleService.getArticle(1))
                .thenReturn(problem);
        JSONObject quiz = new JSONObject();
        quiz.put("problem", "문제");
        quiz.put("choices", List.of("문항1", "문항2", "문항3"));
        quiz.put("answer", 1);
        when(quizService.getQuiz(1))
                .thenReturn(List.of(quiz, quiz, quiz));
        mvc.perform(get("/problem/1")
                        .header("Authorization", "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.problem.content[0][0]").value("“거인이 내 뒤로 뚜벅뚜벅 쫓아오는 소리를 항상 들어야 한다고 생각해보게. 그때 그 기분을 자네는 전혀 상상할 수 없을 걸세.” - 요하네스 브람스"))
                .andExpect(jsonPath("$.data.quiz[0].choices[0]").value("문항1"));

    }

    @Test
    @WithUserDetails("test123")
    public void problemSolve_test() throws Exception {
        String content = objectMapper.writeValueAsString(ProblemRequestDTO
                .builder()
                .userId("test123")
                .keyword(List.of("1","2","3"))
                .sentence("문장|문장|문장")
                .startTime(new Time(20000))
                .finishTime(new Time(30000))
                .totalTime(new Time(10000))
                .quizId(List.of(1,2,3))
                .quizChoice(List.of(1,2,3))
                .quizResult(List.of("Y","Y","Y")).build());
        when(articleService.getSummarization(1))
                .thenReturn("요약결과");
        mvc.perform(post("/problem/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.ai_summarization").value("요약결과"));

    }

    @Test
    @WithUserDetails("test123")
    public void problemResult_test() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time", new Time(10000));
        jsonObject.put("keyword", List.of("문항", "문항", "문항"));
        jsonObject.put("sentence", List.of("주제문", "주제문", "주제문"));
        jsonObject.put("summarization", "요약");
        jsonObject.put("ai-summarization", "ai요약");
        when(tbReadService.getReadResult(1, "test123"))
                .thenReturn(jsonObject);

        mvc.perform(get("/problem/1/users/test123")
                        .header("Authorization", "token"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("test123")
    public void problemBookmark_test() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "test123");
        String content = objectMapper.writeValueAsString(jsonObject);
        mvc.perform(post("/problem/1/bookmark")
                .header("Authorization", "")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    @WithUserDetails("test123")
    public void problemBookmarkList_test() throws Exception{
        when(saveArticleService.getBookmarkArticles("test123", 1))
                .thenReturn(List.of(ArticleDTO.builder()
                        .atcTitle("test").build()));
        mvc.perform(get("/problem/bookmark/users/test123")
                .header("Authorization",""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].atcTitle").value("test"));

    }

}