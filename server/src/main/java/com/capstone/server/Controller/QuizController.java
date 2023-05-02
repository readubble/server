package com.capstone.server.Controller;

import com.capstone.server.DTO.ResponseDTO.ListResultReponseDTO;
import com.capstone.server.DTO.ResponseDTO.WordQuizResultDTO;
import com.capstone.server.Service.WordQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class QuizController {
    private final WordQuizService wordQuizService;
    @Autowired
    public QuizController(WordQuizService wordQuizService){
        this.wordQuizService = wordQuizService;
    }

    @GetMapping("/quiz/{id}")
    public ListResultReponseDTO WordQuizList(@PathVariable("id") String userId){
        List<WordQuizResultDTO> wordQuizResult = wordQuizService.wordQuizList(userId);
        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(wordQuizResult).build();
    }

}
