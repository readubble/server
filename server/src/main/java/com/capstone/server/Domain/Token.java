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
public class Token {
    @Id
    private String userId;
    private String token;
    private boolean status; //login:true, logout:false

    @Builder
    public Token(String userId, String token, boolean status){
        this.userId = userId;
        this.token = token;
        this.status = status;
    }
}
