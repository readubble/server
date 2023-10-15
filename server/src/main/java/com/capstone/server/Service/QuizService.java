package com.capstone.server.Service;

import com.capstone.server.Domain.Quiz;
import com.capstone.server.Repository.QuizItemRepository;
import com.capstone.server.Repository.QuizRepository;
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
    public List<JSONObject> getQuiz(int id){
        List<Quiz> quiz = quizRepository.findAllByTbArticleId(id);
        List<JSONObject> results = getQuizProblem(id, quiz);
        return results;
    }

    private List<JSONObject> getQuizProblem(int id, List<Quiz> quiz) {
        List<JSONObject> results = new ArrayList<>();
        for(int i = 0; i< quiz.size(); i++){
            JSONObject result = new JSONObject();
            result.put("problem", quiz.get(i).getQuizQuestion());
            List<String> item = quizItemRepository.findAllByTbArticleIdAndTbQuizNo(id, quiz.get(i).getQuizNo()).stream().map(r -> r.getItemValue()).collect(Collectors.toList());
            result.put("choices", item);
            result.put("answer", quiz.get(i).getQuizAns());
            results.add(result);
        }
        return results;
    }


}
