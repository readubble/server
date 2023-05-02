package com.capstone.server.Service;

import com.capstone.server.Repository.WordQuizAnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WordQuizAnswerService {
    private final WordQuizAnswerRepository wordQuizAnswerRepository;
    @Autowired
    public WordQuizAnswerService(WordQuizAnswerRepository wordQuizAnswerRepository){
        this.wordQuizAnswerRepository = wordQuizAnswerRepository;
    }
}
