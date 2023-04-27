package com.capstone.server.DTO;

import com.capstone.server.Domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String userNm;
    private String userPw;
    private Date joinDt;
    private String userLevel;
    private int userExp;
    private String userPhotoIn;
    private String role;

    @Builder
    public UserDTO(String id, String userNm, String userPw, Date joinDt, String userLevel, int userExp, String userPhotoIn, String role){
        this.id = id;
        this.userNm = userNm;
        this.userPw = userPw;
        this.joinDt = joinDt;
        this.userLevel = userLevel;
        this.userExp = userExp;
        this.userPhotoIn = userPhotoIn;
        this.role = role;
    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .userNm(userNm)
                .userPw(userPw)
                .joinDt(joinDt)
                .userLevel(userLevel)
                .userExp(userExp)
                .userPhotoIn(userPhotoIn)
                .role(role)
                .build();
    }

    public UserDTO fromEntity(User user){
        return UserDTO.builder()
                .id(user.getId())
                .userNm(user.getUserNm())
                .userPw(user.getUserPw())
                .joinDt(user.getJoinDt())
                .userLevel(user.getUserLevel())
                .userExp(user.getUserExp())
                .userPhotoIn(user.getUserPhotoIn())
                .role(user.getRole())
                .build();
    }
}
