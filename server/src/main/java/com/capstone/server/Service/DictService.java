package com.capstone.server.Service;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.Domain.Dict;
import com.capstone.server.Domain.Search;
import com.capstone.server.Etc.DictKey;
import com.capstone.server.Repository.DictRepository;
import com.capstone.server.Repository.SearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class DictService {
    private final DictRepository dictRepository;
    private final SearchRepository searchRepository;
    @Autowired
    public DictService(DictRepository dictRepository, SearchRepository searchRepository){
        this.dictRepository = dictRepository;
        this.searchRepository = searchRepository;
    }

    @Builder
    @Data
    private static class Response{
        Boolean status;
        ResponseEntity<String> response;

        public Response(Boolean status){
            this.status = status;
        }
        public Response(Boolean status, ResponseEntity<String> response){
            this.status = status;
            this.response = response;
        }
    }

    private Response getDictApi(String keyword, int start, int num){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> DictRequest = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://stdict.korean.go.kr/api/search.do?key="+DictKey.DictKey+"&q="+keyword+"&req_type=json"+"&start="+start+"&num="+num,
                    HttpMethod.GET,
                    DictRequest,
                    String.class
            );
            return new Response(true, response);
        }catch (Exception e){
            return new Response(false);
            //db에서 받아오기
        }
    }

    public Boolean checkStatus(Response response){
        if(response.status && response.response.getStatusCode().equals(HttpStatus.OK)){
            return true;
        }else{
            return false;
        }
    }

    public List<DictDTO> getDictInfo(String keyword, String userId) throws IOException {
        List<DictDTO> result = new ArrayList<>();
        Response response = getDictApi(keyword, 1, 10);
        if(checkStatus(response)){ //응답 정상
            String responseBody = response.response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            if(jsonNode.get("channel").get("total").asInt()>jsonNode.get("channel").get("total").asInt()){
                //total이 큰 경우 -> 목록 다시 받아오기
                response = getDictApi(keyword, 1, jsonNode.get("channel").get("total").asInt());
                if(!checkStatus(response)){
                    //DB에서 받아오기
                    return getDictInfoDB(keyword, userId);
                }
            }

            //total이 커서 재시도 후 정상적으로 값을 받아왔거나, total이 num보다 적은 경우
            //API의 결과값을 그대로 반환해주면 된다.
            Date date = new Date();
            //List<JsonNode> items = jsonNode.get("channel").findValues("item");
            Iterator<JsonNode> items = jsonNode.get("channel").get("item").iterator();
            while(items.hasNext()){
                JsonNode tmp = items.next();
                DictDTO dictDTO = new DictDTO(tmp.get("target_code").asInt(), tmp.get("word").asText(), tmp.get("target_code").asInt(),
                        tmp.get("sense").get("definition").asText());
                result.add(dictDTO);
                Search search = new Search(userId, tmp.get("target_code").asInt(), "N", date);
                searchRepository.save(search);
            }
            return result;

        }else{ //서버 통신 오류 -> DB에서 받아오기
            return getDictInfoDB(keyword, userId);

        }
    }

    public List<DictDTO> getDictInfoDB(String keyword, String userId){
        List<DictDTO> result = dictRepository.findAllByWordNm(keyword);
        Date date = new Date();
        for(int i=0; i<result.size(); i++){
            Search search = new Search(userId, result.get(i).getWordNo(), "N", date);
            searchRepository.save(search);
        }
        return result;
    }
}
