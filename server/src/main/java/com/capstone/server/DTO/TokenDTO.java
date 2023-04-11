package com.capstone.server.DTO;

import com.capstone.server.Domain.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String userId;
    private String token;
    private boolean status; //login:true, logout:false

    @Builder
    public TokenDTO(String userId, String token, boolean status){
        this.userId = userId;
        this.token = token;
        this.status = status;
    }

    public Token toToken(){
        return Token.builder()
                .userId(userId)
                .token(token)
                .status(status)
                .build();
    }

    public TokenDTO fromToken(Token token){
        return TokenDTO.builder()
                .userId(token.getUserId())
                .token(token.getToken())
                .status(token.isStatus())
                .build();
    }
}
