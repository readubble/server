package com.capstone.server.Domain;

import com.capstone.server.Domain.PK.SearchPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@IdClass(SearchPK.class)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Search {
    @Id
    private String tbUserId;
    @Id
    private int tbDictWordNo;
    @Column(columnDefinition = "char")
    private String saveFl;
    private Date searchDt;

    @Builder
    public Search(String tbUserId, int tbDictWordNo, String saveFl, Date searchDt) {
        this.tbUserId = tbUserId;
        this.tbDictWordNo = tbDictWordNo;
        this.saveFl = saveFl;
        this.searchDt = searchDt;
    }

    public void setSaveFl(String saveFl) {
        this.saveFl = saveFl;
    }
}
