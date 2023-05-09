package com.capstone.server.Domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Category {
    @Id
    private int id;
    private String cgNm;

    @Builder
    public Category(int id, String cgNm) {
        this.id = id;
        this.cgNm = cgNm;
    }
}
