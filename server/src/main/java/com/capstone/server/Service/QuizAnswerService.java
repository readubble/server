package com.capstone.server.Service;

import com.capstone.server.Repository.QuizAnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuizAnswerService {
    private final QuizAnswerRepository quizAnswerRepository;

    @Autowired
    public QuizAnswerService(QuizAnswerRepository quizAnswerRepository){
        this.quizAnswerRepository = quizAnswerRepository;
    }

    public int getUserQuizAnswer(String userId, String difficulty){
        int result = quizAnswerRepository.countAllByCorrectFlIsAndTbUserIdJoinArticle("Y", userId, difficulty);
        return result;
    }
}
