package com.capstone.server.Service;

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
import java.util.List;
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

    public List<ArticleInterface> getArticles(String userId, int category, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<ArticleInterface> result = articleRepository.findByArticleLEFTJOINTbRead(userId, category, pageable).toList();
        return result;
    }

    public JSONObject getArticle(int id){
        Article article = articleRepository.findById(id);
        JSONObject result = new JSONObject();
        List<List> content = separateArticle(article);
        result.put("title", article.getAtcTitle());
        result.put("content", content);
        result.put("level", article.getDifficulty());
        result.put("author", article.getAtcWriter());
        return result;

    }

    private List<List> separateArticle(Article article){
        String[] articleParagraphs = article.getAtcText().split("\n"); //문단별로 끊기
        List<List> content = new ArrayList<List>();
        for(int i=0; i<articleParagraphs.length; i++){
            Pattern pattern = Pattern.compile("^“[가-힣]*.*”\\s-\\s[가-힣|\\s]*|“*[ㄱ-ㅎ가-힣a-zA-Z0-9\\s\\[\\]\\‘\\’\\\\'\\\",\\“\\”\\-\\\\(\\)\\『\\』\\~\\·\\*]*”*[?|!][\\s]|“*[ㄱ-ㅎ가-힣a-zA-Z0-9\\s\\[\\]\\‘\\’\\\\'\\\",\\“\\”\\-\\\\(\\)\\『\\』\\~\\·\\*\\?\\!]*”*[.]");
            Matcher matcher = pattern.matcher(articleParagraphs[i]);
            List sentences = new ArrayList<>();
            while(matcher.find()){
                sentences.add(matcher.group());
            }
            content.add(sentences);
        }
        return content;
    }

    public String getSummarization(int id){
        Article article = articleRepository.findById(id);
        return article.getAnsSmr();
    }
}
