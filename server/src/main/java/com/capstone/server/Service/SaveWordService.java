package com.capstone.server.Service;

import com.capstone.server.DTO.SaveWordDTO;
import com.capstone.server.Domain.Dict;
import com.capstone.server.Domain.SaveWord;
import com.capstone.server.Repository.DictRepository;
import com.capstone.server.Repository.SaveWordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;

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

    // 사용자가 검색한 word가 데이터베이스에 존재하는지 확인
    // 그러기 위해서는 DictRepository에 접근하여 검색어가 존재하는지 확인
//    SaveWord
//    @Id
//    private int saveNo;
//    private String tbUserId;
//    private int tbWordNo;
//    private String wordNm;
    // 저장
    public void saveWord(String tbUserId, int targetCode) {
        // 1. Dict(단어)테이블에 wordNm(단어명)으로 검색을 쫙 하고 dict 변수에 넣어줌
        Dict dict = dictRepository.findByTargetCode(targetCode);
        Boolean saveWordCheck = saveWordRepository.existsByTbUserIdAndTargetCode(tbUserId, targetCode);
        // tbUserId와 wordNm 필드가 일치하는 SaveWord 엔티티 객체를 조회
        // saveWordCheck 변수에는 tbUserId와 wordNm이 일치하는 SaveWord 엔티티 객체들의 리스트가 담겨지게 됩니다.
        // 이후 saveWordCheck 변수의 값에 따라서 저장된 데이터가 이미 있는지 여부를 판단하여 중복 저장을 방지할 수 있습니다.
        if (dict != null && saveWordCheck) {
            // 2. dict가 null값이 아니라면, 즉 Dict 테이블에 단어가 존재하고, 이미 saveWord 테이블에 내가 저장할 Word가 있는지 체크
            int tbWordNo = dict.getWordNo(); // 단어의 word_no(pk)를 tbWordNo에 넣어준다. -> save_word에 저장
            // 3. Dict 테이블에 단어가 있으면서, 이 사용자가 북마크에 저장하지는 않은 단어이므로 => SaveWord 테이블에 저장
            SaveWord saveWord = SaveWord.builder() // builider()는 생성자 파라미터를 주입하고 엔티티 객체를 생성해주는 메소드
                    .tbUserId(tbUserId)
                    .targetCode(targetCode)
                    .wordNm(dict.getWordNm())
                    .build();
            saveWordRepository.save(saveWord); // saveWord객체를 save한다.
        }
    }

    // deleteWord 메서드는 findByTbUserIdAndWordNm 메서드를 사용하여 저장된 단어를 찾고, deleteAll 메서드를 이용해 해당 단어를 삭제
    // 삭제
    public void deleteWord(String userId, int targetCode) {
        SaveWord saveWord = saveWordRepository.findByTbUserIdAndTargetCode(userId, targetCode);
        saveWordRepository.delete(saveWord);
        // deleteBy
    }

    // getSavedWords 메서드는 findAllByTbUserId 메서드를 사용하여 해당 사용자가 저장한 단어를 조회
    // 조회
    public List<SaveWord> getSavedWords(String userId) {
        return saveWordRepository.findAllByTbUserId(userId);
    }

}
