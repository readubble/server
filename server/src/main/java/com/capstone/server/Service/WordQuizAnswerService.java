package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.WordQuizRequestDTO;
import com.capstone.server.Domain.WordQuizAnswer;
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

    public void wordQuizSave(WordQuizRequestDTO wordQuizRequestDTO){
        WordQuizAnswer wordQuizAnswer = new WordQuizAnswer(wordQuizRequestDTO.getUserId(), wordQuizRequestDTO.getQuizId(), wordQuizRequestDTO.getQuizChoice(), wordQuizRequestDTO.getQuizResult());
        wordQuizAnswerRepository.save(wordQuizAnswer);
    }
}
