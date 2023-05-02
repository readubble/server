package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.WordQuizRequestDTO;
import com.capstone.server.Domain.WordQuiz;
import com.capstone.server.Domain.WordQuizAnswer;
import com.capstone.server.Repository.WordQuizAnswerRepository;
import com.capstone.server.Repository.WordQuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WordQuizAnswerService {
    private final WordQuizAnswerRepository wordQuizAnswerRepository;
    private final WordQuizRepository wordQuizRepository;
    @Autowired
    public WordQuizAnswerService(WordQuizAnswerRepository wordQuizAnswerRepository, WordQuizRepository wordQuizRepository){
        this.wordQuizAnswerRepository = wordQuizAnswerRepository;
        this.wordQuizRepository = wordQuizRepository;
    }

    public void wordQuizSave(WordQuizRequestDTO wordQuizRequestDTO){
        WordQuizAnswer wordQuizAnswer = new WordQuizAnswer(wordQuizRequestDTO.getUserId(), wordQuizRequestDTO.getQuizId(), wordQuizRequestDTO.getQuizChoice(), wordQuizRequestDTO.getQuizResult());
        wordQuizAnswerRepository.save(wordQuizAnswer);
    }

    public String wordQuizInfo(String userId){
        List<WordQuiz> quiz = wordQuizRepository.findTop3By();
        String result="";
        for(int i=0; i<3; i++){
            Optional<WordQuizAnswer> wordQuizAnswer = wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizQuizNo(userId, quiz.get(i).getQuizNo());
            if(wordQuizAnswer.isPresent()){
                if(wordQuizAnswer.get().getCorrectFl().equals("Y")) {
                    result += "T";
                }else{
                    result+="F";
                }
            }else{
                result+="N";
            }
        }
        return result;
    }
}
