package com.capstone.server.Service;

import com.capstone.server.DTO.ArticleDTO;
import com.capstone.server.Interface.ArticleInterface;
import com.capstone.server.Repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    @Autowired
    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public List<ArticleInterface> articleList(String userId, String category, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<ArticleInterface> result = articleRepository.findByArticleLEFTJOINTbRead(userId, category, pageable).toList();
        return result;
    }
}
