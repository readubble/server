package com.capstone.server.Service;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.DTO.ResponseDTO.DictResponseDTO;
import com.capstone.server.Domain.SaveWord;
import com.capstone.server.Domain.Search;
import com.capstone.server.Etc.DictKey;
import com.capstone.server.Repository.DictRepository;
import com.capstone.server.Repository.SaveWordRepository;
import com.capstone.server.Repository.SearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class DictService {
    private final DictRepository dictRepository;
    private final SearchRepository searchRepository;
    private final SaveWordRepository saveWordRepository;
    @Autowired
    public DictService(DictRepository dictRepository, SearchRepository searchRepository, SaveWordRepository saveWordRepository){
        this.dictRepository = dictRepository;
        this.searchRepository = searchRepository;
        this.saveWordRepository = saveWordRepository;
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


    public List<DictResponseDTO> getDictionary(String keyword, String userId) throws IOException {
        List<DictResponseDTO> result = new ArrayList<>();
        Response response = getDictionaryApi(keyword, 1, 10);
        if(isResponseAvailable(response)){ //응답 정상
            String responseBody = response.response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            if(jsonNode.get("channel").get("total").asInt()>jsonNode.get("channel").get("total").asInt()){
                response = getDictionaryApi(keyword, 1, jsonNode.get("channel").get("total").asInt());
                if(!isResponseAvailable(response)){
                    return getDictionaryDB(keyword, userId);
                }
            }
            saveSearchHistory(userId, result, jsonNode);
            return result;

        }else{
            return getDictionaryDB(keyword, userId);

        }
    }

    public Boolean isResponseAvailable(Response response){
        if(response.status && response.response.getStatusCode().equals(HttpStatus.OK)){
            return true;
        }else{
            return false;
        }
    }

    private Response getDictionaryApi(String keyword, int start, int num){
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
        }
    }


    public List<DictResponseDTO> getDictionaryDB(String keyword, String userId){
        List<DictDTO> result = dictRepository.findAllByWordNmStartingWith(keyword);
        List<DictResponseDTO> responseResult = new ArrayList<>();
        saveSearchHistory(userId, result, responseResult);
        return responseResult;
    }

    private void saveSearchHistory(String userId, List<DictResponseDTO> result, JsonNode jsonNode) {
        Date date = new Date();
        Iterator<JsonNode> items = jsonNode.get("channel").get("item").iterator();
        while(items.hasNext()){
            JsonNode tmp = items.next();
            DictResponseDTO dictResponseDTO= new DictResponseDTO(tmp.get("target_code").asInt(), tmp.get("word").asText(),
                    tmp.get("sense").get("definition").asText(), "N");;
            Optional<Search> search = searchRepository.findByTbUserIdAndTbDictWordNo(userId, tmp.get("target_code").asInt());
            if(search.isEmpty()) {
                Search searchObj = new Search(userId, tmp.get("target_code").asInt(), "N", date);
                searchRepository.save(searchObj);
            }else{
                Optional<SaveWord> saveWord = saveWordRepository.findByTbUserIdAndTargetCodeAndWordNmAndWordMean(userId, tmp.get("target_code").asInt(), tmp.get("word").asText(),tmp.get("sense").get("definition").asText());
                if(saveWord.isPresent()){
                    dictResponseDTO.setSaveFl("Y");
                }
            }
            result.add(dictResponseDTO);
        }
    }
    private void saveSearchHistory(String userId, List<DictDTO> result, List<DictResponseDTO> responseResult) {
        Date date = new Date();
        for(int i = 0; i< result.size(); i++){
            Optional<Search> search = searchRepository.findByTbUserIdAndTbDictWordNo(userId, result.get(i).getTargetCode());
            DictResponseDTO dictResponseDTO = DictResponseDTO.builder()
                    .targetCode(result.get(i).getTargetCode())
                    .wordNm(result.get(i).getWordNm())
                    .wordMean(result.get(i).getWordMean())
                    .saveFl("N").build();
            if(search.isEmpty()) {
                Search searchObj = new Search(userId, result.get(i).getTargetCode(), "N", date);
                searchRepository.save(searchObj);
            }else{
                Optional<SaveWord> saveWord = saveWordRepository.findByTbUserIdAndTargetCodeAndWordNmAndWordMean(userId, result.get(i).getTargetCode(), result.get(i).getWordNm(), result.get(i).getWordMean());
                if(saveWord.isPresent()){
                    dictResponseDTO.setSaveFl("Y");
                }
            }
            responseResult.add(dictResponseDTO);
        }
    }
}
