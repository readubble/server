package com.capstone.server.Domain;

import com.capstone.server.Domain.PK.SearchPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

@Entity
@Getter
@IdClass(SearchPK.class)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Search {
    @Id
    private String UserId;
    @Id
    private int DictWordNo;
    private String saveFl;
    private Date searchDt;

    @Builder
    public Search(String userId, int dictWordNo, String saveFl, Date searchDt) {
        UserId = userId;
        DictWordNo = dictWordNo;
        this.saveFl = saveFl;
        this.searchDt = searchDt;
    }
}
