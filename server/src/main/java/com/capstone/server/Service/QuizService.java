package com.capstone.server.Service;

import com.capstone.server.Domain.Quiz;
import com.capstone.server.Domain.QuizItem;
import com.capstone.server.Repository.QuizItemRepository;
import com.capstone.server.Repository.QuizRepository;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizItemRepository quizItemRepository;
    @Autowired
    public QuizService(QuizRepository quizRepository, QuizItemRepository quizItemRepository){
        this.quizRepository = quizRepository;
        this.quizItemRepository = quizItemRepository;
    }
    public List<JSONObject> Quiz(int id){
        List<JSONObject> result = new ArrayList<>();
        List<Quiz> quiz = quizRepository.findAllByTbArticleId(id);
        for(int i=0; i<quiz.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("problem", quiz.get(i).getQuizQuestion());
            List<String> item = quizItemRepository.findAllByTbQuizQuizNo(quiz.get(i).getQuizNo()).stream().map(r -> r.getItemValue()).collect(Collectors.toList());
            jsonObject.put("choices", item);
            jsonObject.put("answer", quiz.get(i).getQuizAns());
            result.add(jsonObject);
        }
        return result;
    }
}
