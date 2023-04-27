package com.capstone.server.DTO;

import com.capstone.server.Domain.Search;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
public class SearchDTO {
    private String UserId;
    private int DictWordNo;
    private String saveFl;
    private Date searchDt;


    @Builder
    public SearchDTO(String userId, int dictWordNo, String saveFl, Date searchDt) {
        UserId = userId;
        DictWordNo = dictWordNo;
        this.saveFl = saveFl;
        this.searchDt = searchDt;
    }

    public Search toEntity(){
        return Search.builder()
                .userId(UserId)
                .saveFl(saveFl)
                .searchDt(searchDt)
                .dictWordNo(DictWordNo).build();
    }
    public SearchDTO fromEntity(Search search){
        return SearchDTO.builder()
                .userId(search.getUserId())
                .saveFl(search.getSaveFl())
                .searchDt(search.getSearchDt())
                .dictWordNo(search.getDictWordNo()).build();
    }
}
