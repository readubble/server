package com.capstone.server.Domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class User {
    @Id
    private String id;
    private String userNm;
    private String userPw;
    private Date joinDt;
    private String userLevel;
    private int userExp;
    private String userPhotoIn;
    private String role;

    @Builder
    public User(String id, String userNm, String userPw, Date joinDt, String userLevel, int userExp, String userPhotoIn, String role){
        this.id = id;
        this.userNm = userNm;
        this.userPw = userPw;
        this.joinDt = joinDt;
        this.userLevel = userLevel;
        this.userExp = userExp;
        this.userPhotoIn = userPhotoIn;
        this.role = role;
    }

    public List<String> getRoleList(){
        if (this.role.length() >0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
}
