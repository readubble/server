package com.capstone.server.Service;

import com.capstone.server.DTO.ResponseDTO.WordQuizResultDTO;
import com.capstone.server.Domain.WordQuiz;
import com.capstone.server.Domain.WordQuizAnswer;
import com.capstone.server.Domain.WordQuizItem;
import com.capstone.server.Repository.WordQuizAnswerRepository;
import com.capstone.server.Repository.WordQuizItemRepository;
import com.capstone.server.Repository.WordQuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WordQuizService {
    private final WordQuizRepository wordQuizRepository;
    private final WordQuizItemRepository wordQuizItemRepository;
    private final WordQuizAnswerRepository wordQuizAnswerRepository;
    @Autowired
    public WordQuizService(WordQuizRepository wordQuizRepository, WordQuizItemRepository wordQuizItemRepository, WordQuizAnswerRepository wordQuizAnswerRepository){
        this.wordQuizRepository = wordQuizRepository;
        this.wordQuizItemRepository = wordQuizItemRepository;
        this.wordQuizAnswerRepository = wordQuizAnswerRepository;
    }

    public List<WordQuizResultDTO> getWordQuizzes(String userId){
        List<WordQuizResultDTO> result = new ArrayList<>();
        List<WordQuiz> quizzes = wordQuizRepository.findTop3By();
        for(int i=0; i<3; i++){
            WordQuiz wordQuiz = quizzes.get(i);
            List<String> quizItems = getQuizItems(wordQuiz);
            Optional<WordQuizAnswer> quizSolved = wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizNo(userId, wordQuiz.getQuizNo());
            WordQuizResultDTO wordQuizResultDTO = getWordQuizResult(quizSolved, wordQuiz, quizItems);
            result.add(wordQuizResultDTO);
        }
        return result;
    }

    public List<String> getQuizItems(WordQuiz wordQuiz){
        List<WordQuizItem> quizChoices = wordQuizItemRepository.findAllByWordQuizNo(wordQuiz.getQuizNo());
        List<String> quizItems = new ArrayList<>();
        for(int j=0; j<quizChoices.size(); j++) {
            quizItems.add(quizChoices.get(j).getItemValue());
        }
        return quizItems;
    }
    public WordQuizResultDTO getWordQuizResult(Optional<WordQuizAnswer> quizSolved, WordQuiz wordQuiz, List<String> quizItems){
        if(quizSolved.isPresent()) {
            return new WordQuizResultDTO(wordQuiz.getQuizNo(),
                    wordQuiz.getQuizQuestion(), quizItems, wordQuiz.getQuizAns(), "Y", quizSolved.get().getQuizInp());
        }else{
            return new WordQuizResultDTO(wordQuiz.getQuizNo(),
                    wordQuiz.getQuizQuestion(), quizItems, wordQuiz.getQuizAns(), "N", -1);
        }
    }
}
