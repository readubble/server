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

    public List<WordQuizResultDTO> wordQuizList(String userId){
        List<WordQuizResultDTO> result = new ArrayList<>();
        List<WordQuiz> quiz_items = wordQuizRepository.findTop3By();
        for(int i=0; i<3; i++){
            WordQuiz wordQuiz = quiz_items.get(i);
            List<WordQuizItem> quiz_item_items_data = wordQuizItemRepository.findAllByTbWordQuizQuizNo(wordQuiz.getQuizNo());
            List<String> quiz_item_items = new ArrayList<>();
            for(int j=0; j<quiz_item_items_data.size(); j++) {
                quiz_item_items.add(quiz_item_items_data.get(i).getItemValue());
            }
            Optional<WordQuizAnswer> quiz_solved = wordQuizAnswerRepository.findByTbUserIdAndTbWordQuizQuizNo(userId, wordQuiz.getQuizNo());
            if(quiz_solved.isPresent()) {
                WordQuizResultDTO wordQuizResultDTO = new WordQuizResultDTO(wordQuiz.getQuizNo(),
                        wordQuiz.getQuizQuestion(), quiz_item_items, wordQuiz.getQuizAns(), "Y", quiz_solved.get().getQuizInp());
                result.add(wordQuizResultDTO);
            }else{
                WordQuizResultDTO wordQuizResultDTO = new WordQuizResultDTO(wordQuiz.getQuizNo(),
                        wordQuiz.getQuizQuestion(), quiz_item_items, wordQuiz.getQuizAns(), "N", -1);
                result.add(wordQuizResultDTO);
            }
        }

        return result;

    }
}
