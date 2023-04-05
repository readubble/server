package com.capstone.server.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginRequestDTO {
    private String id;
    private String password;

    @JsonCreator
    @Builder
    public LoginRequestDTO(@JsonProperty("id") String id, @JsonProperty("password") String password){
        this.id = id;
        this.password = password;
    }
}
