package com.capstone.server.DTO;

import com.capstone.server.Domain.Search;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class SearchDTO {
    private String tbUserId;
    private int tbDictWordNo;
    private String saveFl;
    private Date searchDt;


    @Builder
    public SearchDTO(String tbUserId, int tbDictWordNo, String saveFl, Date searchDt) {
        this.tbUserId = tbUserId;
        this.tbDictWordNo = tbDictWordNo;
        this.saveFl = saveFl;
        this.searchDt = searchDt;
    }

    public Search toEntity(){
        return Search.builder()
                .tbUserId(tbUserId)
                .saveFl(saveFl)
                .searchDt(searchDt)
                .tbDictWordNo(tbDictWordNo).build();
    }
    public SearchDTO fromEntity(Search search){
        return SearchDTO.builder()
                .tbUserId(search.getTbUserId())
                .saveFl(search.getSaveFl())
                .searchDt(search.getSearchDt())
                .tbDictWordNo(search.getTbDictWordNo()).build();
    }
}
