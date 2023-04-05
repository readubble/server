package com.capstone.server.Service;

import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.Repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
