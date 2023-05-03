package com.capstone.server.Controller;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.DTO.ResponseDTO.ListResultReponseDTO;
import com.capstone.server.Service.DictService;
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
    @Autowired
    public WordController(DictService dictService){
        this.dictService = dictService;
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

}
