package com.capstone.server.Service;

import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.Domain.Token;
import com.capstone.server.Exception.ApiException;
import com.capstone.server.Exception.ExceptionEnum;
import com.capstone.server.Repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void getUsername_unvalid_token_test(){
        assertThatThrownBy(() -> tokenService.getUsername("")).isInstanceOf(ApiException.class)
                .hasMessage(ExceptionEnum.TOKEN_ERROR.getMessage());
    }

    @Test
    void setLoginStatus_test(){
        when(tokenRepository.findById(anyString()))
                .thenReturn(Optional.of(Token.builder()
                        .userId("test123")
                        .token("")
                        .status(false).build()));
        tokenService.setLoginStatus("test123");
        verify(tokenRepository).save(argThat(Token->Token.isStatus()));
    }

    @Test
    void token_update_test(){
        when(tokenRepository.findById(anyString()))
                .thenReturn(Optional.of(Token.builder().userId("test123").token("token").status(true).build()));
        tokenService.TokenUpdate("test123", "");
        verify(tokenRepository).save(argThat(token->token.getToken().equals("") && !token.isStatus()));
    }


}