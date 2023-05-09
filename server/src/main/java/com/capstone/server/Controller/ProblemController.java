package com.capstone.server.Controller;

import com.capstone.server.DTO.RequestDTO.ProblemRequestDTO;
import com.capstone.server.DTO.ResponseDTO.ListResultReponseDTO;
import com.capstone.server.DTO.ResponseDTO.ProblemResponseDTO;
import com.capstone.server.DTO.ResponseDTO.ResultResponseDTO;
import com.capstone.server.Interface.ArticleInterface;
import com.capstone.server.Service.ArticleService;
import com.capstone.server.Service.QuizService;
import com.capstone.server.Service.TbReadService;
import com.capstone.server.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ProblemController {
    private final ArticleService articleService;
    private final QuizService quizService;
    private final TbReadService tbReadService;
    @Autowired
    public ProblemController(ArticleService articleService, QuizService quizService, TbReadService tbReadService){
        this.articleService = articleService;
        this.quizService = quizService;
        this.tbReadService = tbReadService;
    }

    @GetMapping("/problem/users/{id}")
    public ListResultReponseDTO problemList(@PathVariable("id") String userId, @RequestParam(name="category", required=true) String category, @RequestParam(name="page", required = false, defaultValue = "0") int page, @RequestParam(name="size", required = false, defaultValue = "5") int size){
        List<ArticleInterface> result = articleService.articleList(userId, category, page, size);
        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result).build();
    }

    @GetMapping("/problem/{id}")
    public ProblemResponseDTO problem(@PathVariable("id")int id){
        JSONObject problem = articleService.article(id);
        List<JSONObject> quiz = quizService.Quiz(id);
        return ProblemResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ProblemResponseDTO.Data.builder()
                        .problem(problem)
                        .quiz(quiz).build()).build();
    }

    @PostMapping("/problem/{id}")
    public ResultResponseDTO problemSolve(@PathVariable("id") int id, @RequestBody ProblemRequestDTO problemRequestDTO){
        tbReadService.save(problemRequestDTO, id);
        String summarization = articleService.getSummarization(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ai_summarization", summarization);
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(jsonObject).build();

    }

    @GetMapping("/problem/{problem_id}/users/{user_id}")
    public ResultResponseDTO problemResult(@PathVariable("problem_id") int problemId, @PathVariable("user_id") String userId){
        JSONObject result = tbReadService.getResult(problemId, userId);
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result).build();
    }
}
