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
    private int targetCode;
    private String wordNm;
    private String wordMean;

    @Builder
    public SaveWordDTO(int saveNo, String tbUserId, int targetCode, String wordNm, String wordMean) {
        this.saveNo = saveNo;
        this.tbUserId = tbUserId;
        this.targetCode = targetCode;
        this.wordNm = wordNm;
        this.wordMean = wordMean;
    }

    public SaveWord toEntity(){
        return SaveWord.builder()
                .saveNo(saveNo)
                .tbUserId(tbUserId)
                .targetCode(targetCode)
                .wordNm(wordNm)
                .wordMean(wordMean).build();
    }

    public SaveWordDTO fromEntity(SaveWord saveWord){
        return SaveWordDTO.builder()
                .saveNo(saveWord.getSaveNo())
                .tbUserId(saveWord.getTbUserId())
                .targetCode(saveWord.getTargetCode())
                .wordNm(saveWord.getWordNm())
                .wordMean(saveWord.getWordMean()).build();
    }
}
