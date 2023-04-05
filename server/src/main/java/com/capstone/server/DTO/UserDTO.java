package com.capstone.server.DTO;

import com.capstone.server.Domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String email;
    private String nickname;
    private String password;
    private String role;

    @Builder
    public UserDTO(String id, String email, String nickname, String password, String role){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public User toUser(){
        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(role)
                .build();
    }

    public UserDTO fromUser(User user){
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
