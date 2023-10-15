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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
                .userNm("tester")
                .userPw("1234")
                .userExp(0)
                .userLevel("L1")
                .joinDt(new Date())
                .userPhotoIn("")
                .role("ROLE_USER")
                .build();
    }
    @Test
    void join_test(){
        //given
        when(userRepository.existsById(userDTO.getId()))
                .thenReturn(false);
        when(userRepository.existsByUserNm(userDTO.getUserNm()))
                .thenReturn(false);

        //when
        userService.join(JoinRequestDTO.builder()
                .id(userDTO.getId())
                .nickname(userDTO.getUserNm())
                .password(userDTO.getUserPw())
                .role(userDTO.getRole()).build());

        //then
        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenService, times(1)).saveToken(any(TokenDTO.class));
    }

    @Test
    void AutoLogin_test(){
        //tokenservice를 실행하는 것이 전부이므로 테스트할 내용이 없음.
    }

    @Test
    void getUserInfo_test(){
        when(userRepository.findById(anyString()))
                .thenReturn(Optional.of(userDTO.toEntity()));
        UserDTO userDTO = userService.getUserInfo("1234");
        verify(userRepository, times(1)).findById(anyString());
        assertThat(userDTO.getUserLevel()).isEqualTo("L1");
    }


}