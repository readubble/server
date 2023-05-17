package com.capstone.server.Service;

import com.capstone.server.DTO.ArticleDTO;
import com.capstone.server.DTO.ResponseDTO.ProblemResponseDTO;
import com.capstone.server.Domain.Article;
import com.capstone.server.Interface.ArticleInterface;
import com.capstone.server.Repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    @Autowired
    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public List<ArticleInterface> articleList(String userId, int category, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<ArticleInterface> result = articleRepository.findByArticleLEFTJOINTbRead(userId, category, pageable).toList();
        return result;
    }

    public JSONObject article(int id){
        Article article = articleRepository.findById(id);
        //문장별로, 문단별로 끊어서
        JSONObject jsonObject = new JSONObject();

        String[] articleArr = article.getAtcText().split("\n"); //문단별로 끊기
        List<List> result = new ArrayList<List>();
        for(int i=0; i<articleArr.length; i++){
            //^“[가-힣\]*.”\s-\s[가-힣|\s]*|[\s]$
            //^[0-9가-힣\s.]*.$
            Pattern pattern = Pattern.compile("^“[가-힣]*.*”\\s-\\s[가-힣|\\s]*|“*[ㄱ-ㅎ가-힣a-zA-Z0-9\\s\\[\\]\\‘\\’\\\\'\\\",\\“\\”\\-\\\\(\\)\\『\\』\\~\\·\\*]*”*[?|!][\\s]|“*[ㄱ-ㅎ가-힣a-zA-Z0-9\\s\\[\\]\\‘\\’\\\\'\\\",\\“\\”\\-\\\\(\\)\\『\\』\\~\\·\\*\\?\\!]*”*[.]");
            Matcher matcher = pattern.matcher(articleArr[i]);
            List tmp_list = new ArrayList<>();
            while(matcher.find()){
                tmp_list.add(matcher.group());
            }
            result.add(tmp_list);
        }
        jsonObject.put("title", article.getAtcTitle());
        jsonObject.put("content", result);
        jsonObject.put("level", article.getDifficulty());
        jsonObject.put("author", article.getAtcWriter());
        return jsonObject;

    }

    public String getSummarization(int id){
        Article article = articleRepository.findById(id);
        return article.getAnsSmr();
    }
}
