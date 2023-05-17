package com.capstone.server.DTO.ResponseDTO;

import com.capstone.server.DTO.DictDTO;
import com.capstone.server.Domain.Dict;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DictResponseDTO {
        private String wordNm;
        private int targetCode;
        private String wordMean;
        private String saveFl;

        @Builder
        public DictResponseDTO(int targetCode, String wordNm, String wordMean, String saveFl) {
            this.wordNm = wordNm;
            this.targetCode = targetCode;
            this.wordMean = wordMean;
            this.saveFl = saveFl;
        }

}
