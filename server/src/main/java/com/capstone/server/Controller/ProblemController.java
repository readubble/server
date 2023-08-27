package com.capstone.server.Controller;

import com.capstone.server.DTO.ArticleDTO;
import com.capstone.server.DTO.RequestDTO.ProblemRequestDTO;
import com.capstone.server.DTO.ResponseDTO.ListResultReponseDTO;
import com.capstone.server.DTO.ResponseDTO.ProblemResponseDTO;
import com.capstone.server.DTO.ResponseDTO.ResponseDTO;
import com.capstone.server.DTO.ResponseDTO.ResultResponseDTO;
import com.capstone.server.Interface.ArticleInterface;
import com.capstone.server.Service.ArticleService;
import com.capstone.server.Service.QuizService;
import com.capstone.server.Service.TbReadService;
import com.capstone.server.Service.SaveArticleService;

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
    private final SaveArticleService saveArticleService;
    @Autowired
    public ProblemController(ArticleService articleService, QuizService quizService, TbReadService tbReadService, SaveArticleService saveArticleService){
        this.articleService = articleService;
        this.quizService = quizService;
        this.tbReadService = tbReadService;
        this.saveArticleService= saveArticleService;
    }

    @GetMapping("/problem/users/{id}")
    public ListResultReponseDTO unresolvedArticles(@PathVariable("id") String userId, @RequestParam(name="category", required=true) int category, @RequestParam(name="page", required = false, defaultValue = "0") int page, @RequestParam(name="size", required = false, defaultValue = "5") int size){
        List<ArticleInterface> messageBody = articleService.getArticles(userId, category, page, size);
        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(messageBody).build();
    }

    @GetMapping("/problem/{id}")
    public ProblemResponseDTO articleWithExercises(@PathVariable("id")int id){
        JSONObject article = articleService.getArticle(id);
        List<JSONObject> exercises = quizService.getQuiz(id);
        return ProblemResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(ProblemResponseDTO.Data.builder()
                        .article(article)
                        .exercises(exercises).build()).build();
    }

    @PostMapping("/problem/{id}")
    public ResultResponseDTO problemCheck(@PathVariable("id") int problemId, @RequestBody ProblemRequestDTO problemRequestDTO){
        tbReadService.saveReadHistory(problemRequestDTO, problemId);
        String summarization = articleService.getSummarization(problemId);
        JSONObject messageBody = new JSONObject();
        messageBody.put("problem_id", problemId);
        messageBody.put("ai_summarization", summarization);
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(messageBody).build();

    }

    @GetMapping("/problem/{problem_id}/users/{user_id}")
    public ResultResponseDTO problemResult(@PathVariable("problem_id") int problemId, @PathVariable("user_id") String userId){
        JSONObject messageBody = tbReadService.getReadResult(problemId, userId);
        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(messageBody).build();
    }

    @PostMapping("/problem/{problem_id}/bookmark")
    public ResponseDTO updateProblemBookmark(@PathVariable("problem_id") int problemId, @RequestBody JSONObject jsonObject){
        String userId = jsonObject.get("user_id").toString();
        tbReadService.updateSaveFl(userId, problemId);
        saveArticleService.bookmarkArticle(userId, problemId);

        return ResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()).build();
    }

    @GetMapping("/problem/bookmark/users/{user-id}")
    public ListResultReponseDTO problemBookmarks(@PathVariable("user-id") String userId, @RequestParam("category") int cgId){
        List<ArticleDTO> messageBody = saveArticleService.getBookmarkArticles(userId, cgId);
        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(messageBody).build();
    }
}
