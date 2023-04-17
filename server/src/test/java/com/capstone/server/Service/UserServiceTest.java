package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.JoinRequestDTO;
import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.DTO.UserDTO;
import com.capstone.server.Domain.User;
import com.capstone.server.Repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.data.relational.core.sql.FalseCondition;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    TokenService tokenService;

    @Mock
    PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserService userService;

    static UserDTO userDTO;

    @BeforeAll
    static void setup(){
        userDTO = UserDTO.builder()
                .id("12345")
                .email("test@tester.com")
                .nickname("tester")
                .password("1234")
                .role("ROLE_USER")
                .build();
    }
    @Test
    void join_test(){
        //given
        when(userRepository.existsByEmail(userDTO.getEmail()))
                .thenReturn(false);
        when(userRepository.existsById(userDTO.getId()))
                .thenReturn(false);
        when(userRepository.existsByNickname(userDTO.getNickname()))
                .thenReturn(false);

        //when
        userService.join(JoinRequestDTO.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .nickname(userDTO.getNickname())
                .password(userDTO.getPassword())
                .role(userDTO.getRole()).build());

        //then
        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenService, times(1)).TokenSave(any(TokenDTO.class));
    }

    @Test
    void AutoLogin_test(){
        //tokenservice를 실행하는 것이 전부이므로 테스트할 내용이 없음.
    }


}