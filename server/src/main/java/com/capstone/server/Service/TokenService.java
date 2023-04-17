package com.capstone.server.Service;

import com.auth0.jwt.JWT;
import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.Domain.Token;
import com.capstone.server.Exception.ApiException;
import com.capstone.server.Exception.ExceptionEnum;
import com.capstone.server.Repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }

    public void TokenSave(TokenDTO tokenDTO){
        tokenRepository.save(tokenDTO.toToken());
    }

    public String getUsername(String token){
        try{
            String username = JWT.decode(token)
                    .getClaim("id")
                    .asString();
            return username;
        } catch (Exception e){
            throw new ApiException(ExceptionEnum.TOKEN_ERROR);
        }
    }

    public void setLoginStatus(String userId){
        Optional<Token> token = tokenRepository.findById(userId);
        if(token.isPresent()) {
            TokenDTO tokenDTO = new TokenDTO(token.get().getUserId(), token.get().getToken(), token.get().isStatus());
            tokenDTO.setStatus(true);
            tokenRepository.save(tokenDTO.toToken());
        } else{
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION); //bad request
        }
    }
}
