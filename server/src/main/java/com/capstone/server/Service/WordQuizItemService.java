package com.capstone.server.Service;

import com.capstone.server.Repository.WordQuizItemRepository;
import com.capstone.server.Repository.WordQuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WordQuizItemService {
    private final WordQuizItemRepository wordQuizItemRepository;
    @Autowired
    public WordQuizItemService(WordQuizItemRepository wordQuizItemRepository){
        this.wordQuizItemRepository = wordQuizItemRepository;
    }
}
