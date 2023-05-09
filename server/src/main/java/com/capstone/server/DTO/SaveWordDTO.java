package com.capstone.server.DTO;

import com.capstone.server.Domain.SaveWord;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveWordDTO {
    private int saveNo;
    private String tbUserId;
    private int tbWordNo;
    private String wordNm;

    @Builder
    public SaveWordDTO(int saveNo, String tbUserId, int tbWordNo, String wordNm) {
        this.saveNo = saveNo;
        this.tbUserId = tbUserId;
        this.tbWordNo = tbWordNo;
        this.wordNm = wordNm;
    }

    public SaveWord toEntity(){
        return SaveWord.builder()
                .saveNo(saveNo)
                .tbUserId(tbUserId)
                .tbWordNo(tbWordNo)
                .wordNm(wordNm).build();
    }

    public SaveWordDTO fromEntity(SaveWord saveWord){
        return SaveWordDTO.builder()
                .saveNo(saveWord.getSaveNo())
                .tbUserId(saveWord.getTbUserId())
                .tbWordNo(saveWord.getTbWordNo())
                .wordNm(saveWord.getWordNm()).build();
    }
}
