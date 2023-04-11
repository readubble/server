package com.capstone.server.Domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class User {
    @Id
    private String id;
    private String email;
    private String nickname;
    private String password;
    private String role;

    @Builder
    public User(String id, String email, String nickname, String password, String role){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public List<String> getRoleList(){
        if (this.role.length() >0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
}
