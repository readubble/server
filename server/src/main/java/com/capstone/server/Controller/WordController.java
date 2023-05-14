package com.capstone.server.Controller;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.DTO.ResponseDTO.ListResultReponseDTO;
import com.capstone.server.DTO.ResponseDTO.ResultResponseDTO;
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
        this.saveWordService = saveWordService; // 진성
        this.searchService = searchService;
    }

    @PostMapping ("/word")
    public ListResultReponseDTO wordSearch(@RequestHeader("Authorization") String Authorization, @RequestBody JSONObject jsonObject){
        String userId = jsonObject.get("id").toString();
        String keyword = jsonObject.get("keyword").toString();
        List<DictDTO> result = dictService.getDictInfoDB(keyword, userId);
        return ListResultReponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(result).build();
    }

    // 단어 북마크
    @PostMapping ("/word/{word-id}/bookmark")
    public ResultResponseDTO wordBookmark(@PathVariable("word_id") int wordId, @RequestBody JSONObject jsonObject){
//     1. Authorization에 토큰 저장 / wordId에 word_id 저장 / BodyParameter는 JSONObject로 받아옴
        String userId = jsonObject.get("user_id").toString();
        searchService.updateSaveFl(userId, wordId);
//     2. jsonObject(BodyParameter)의 user_id 객체를 String으로 변환하여 userId에 저장
        saveWordService.saveWord(userId, wordId);
//     3. SaveWordService에 있는 saveWordBookMark 함수를 통해 SaveWord 테이블에 저장한다.
//        (userId와 wordId를 통해서 저장)

        return ResultResponseDTO.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase()).build();
    }
}
