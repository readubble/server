package com.capstone.server.Controller;

import com.capstone.server.DTO.ResponseDTO.DictResponseDTO;
import com.capstone.server.DTO.ResponseDTO.ListResultReponseDTO;
import com.capstone.server.DTO.ResponseDTO.ResponseDTO;
import com.capstone.server.Domain.SaveWord;
import com.capstone.server.Exception.ApiException;
import com.capstone.server.Exception.ExceptionEnum;
import com.capstone.server.Service.DictService;
import com.capstone.server.Service.SaveWordService;
import com.capstone.server.Service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class WordController {
    private final DictService dictService;

    private final SaveWordService saveWordService; // 진성

    private final SearchService searchService;

    @Autowired
    public WordController(DictService dictService, SaveWordService saveWordService, SearchService searchService) {
        this.dictService = dictService;
        this.saveWordService = saveWordService;
        this.searchService = searchService;
    }

    @PostMapping ("/word")
    public ListResultReponseDTO wordSearch(@RequestHeader("Authorization") String Authorization, @RequestBody JSONObject requestBody){
        String userId = requestBody.get("id").toString();
        String keyword = requestBody.get("keyword").toString();
        try {
            List<DictResponseDTO> messageBody = dictService.getDictionary(keyword, userId);
            return ListResultReponseDTO.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(messageBody).build();
        } catch(Exception e){
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION);
        }
    }

    @PostMapping ("/word/{word-id}/bookmark")
    public ResponseDTO updateWordBookmark(@PathVariable("word-id") int wordId, @RequestBody JSONObject requestBody){
        String userId = requestBody.get("user_id").toString();
        String wordNm = requestBody.get("word_nm").toString();
        String wordMean = requestBody.get("word_mean").toString();
        saveWordService.bookmarkWord(userId, wordId, wordNm, wordMean);

        return ResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()).build();
    }

    @GetMapping("/word/bookmark/users/{user-id}")
    public ListResultReponseDTO wordBookmarks(@PathVariable("user-id") String userId){
        List<SaveWord> messageBody = saveWordService.getBookmarkWords(userId);
        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(messageBody).build();

    }
}
