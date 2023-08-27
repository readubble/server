package com.capstone.server.Service;

import com.capstone.server.Domain.SaveWord;
import com.capstone.server.Repository.DictRepository;
import com.capstone.server.Repository.SaveWordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SaveWordService {

    private final SaveWordRepository saveWordRepository;

    private final DictRepository dictRepository;

    @Autowired
    public SaveWordService(SaveWordRepository saveWordRepository, DictRepository dictRepository) {
        this.saveWordRepository = saveWordRepository;
        this.dictRepository = dictRepository;
    }


    public void bookmarkWord(String tbUserId, int targetCode, String wordNm, String wordMean) {
        Optional<SaveWord> saveWord = saveWordRepository.findByTbUserIdAndTargetCodeAndWordNmAndWordMean(tbUserId, targetCode, wordNm, wordMean);
        if (saveWord.isEmpty()) {
            SaveWord saveWordObj = SaveWord.builder()
                    .tbUserId(tbUserId)
                    .targetCode(targetCode)
                    .wordNm(wordNm)
                    .wordMean(wordMean)
                    .build();
            saveWordRepository.save(saveWordObj);
        }else if(saveWord.isPresent()){
            deleteBookmarkWord(saveWord.get());
        }
    }

    public void deleteBookmarkWord(SaveWord saveWord) {
        saveWordRepository.delete(saveWord);
    }

    public List<SaveWord> getBookmarkWords(String userId){
        List<SaveWord> saveList = saveWordRepository.findAllByTbUserId(userId);
        return saveList;
    }
}
