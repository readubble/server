package com.capstone.server.DTO;

import com.capstone.server.Domain.SaveWord;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveWordDTO {
    private int saveNo;
    private String userId;
    private int fkTbWordNo;
    private String wordNm;

    @Builder
    public SaveWordDTO(int saveNo, String userId, int fkTbWordNo, String wordNm) {
        this.saveNo = saveNo;
        this.userId = userId;
        this.fkTbWordNo = fkTbWordNo;
        this.wordNm = wordNm;
    }

    public SaveWord toEntity(){
        return SaveWord.builder()
                .saveNo(saveNo)
                .userId(userId)
                .fkTbWordNo(fkTbWordNo)
                .wordNm(wordNm).build();
    }

    public SaveWordDTO fromEntity(SaveWord saveWord){
        return SaveWordDTO.builder()
                .saveNo(saveWord.getSaveNo())
                .userId(saveWord.getUserId())
                .fkTbWordNo(saveWord.getFkTbWordNo())
                .wordNm(saveWord.getWordNm()).build();
    }
}
