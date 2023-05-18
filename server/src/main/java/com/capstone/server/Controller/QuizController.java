package com.capstone.server.Controller;

import com.capstone.server.DTO.RequestDTO.WordQuizRequestDTO;
import com.capstone.server.DTO.ResponseDTO.ListResultReponseDTO;
import com.capstone.server.DTO.ResponseDTO.ResponseDTO;
import com.capstone.server.DTO.ResponseDTO.WordQuizResultDTO;
import com.capstone.server.Service.WordQuizAnswerService;
import com.capstone.server.Service.WordQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class QuizController {
    private final WordQuizService wordQuizService;
    private final WordQuizAnswerService wordQuizAnswerService;
    @Autowired
    public QuizController(WordQuizService wordQuizService, WordQuizAnswerService wordQuizAnswerService){
        this.wordQuizService = wordQuizService;
        this.wordQuizAnswerService = wordQuizAnswerService;
    }

    @GetMapping("/quiz/{id}")
    public ListResultReponseDTO WordQuizList(@PathVariable("id") String userId){
        List<WordQuizResultDTO> wordQuizResult = wordQuizService.wordQuizList(userId);
        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(wordQuizResult).build();
    }

    @PostMapping("/quiz")
    public ResponseDTO WordQuizSolve(@RequestBody WordQuizRequestDTO wordQuizRequestDTO){
        wordQuizAnswerService.wordQuizSave(wordQuizRequestDTO);
        return ResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()).build();
    }

}
