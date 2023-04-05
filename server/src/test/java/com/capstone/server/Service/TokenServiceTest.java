package com.capstone.server.Service;

import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.Repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class TokenServiceTest {
    @Mock
    TokenRepository tokenRepository;
    @InjectMocks
    TokenService tokenService;

    @Test
    void tokensave_test(){
        tokenService.TokenSave(TokenDTO.builder()
                .userId("1234")
                .token("token")
                .status(true)
                .build());
        verify(tokenRepository).save(argThat(token->token.getToken().equals("token")));
    }

}