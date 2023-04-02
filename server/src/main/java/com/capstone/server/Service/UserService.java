package com.capstone.server.Service;

import com.capstone.server.DTO.RequestDTO.JoinRequestDTO;
import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.Domain.Token;
import com.capstone.server.Domain.User;
import com.capstone.server.Exception.ApiException;
import com.capstone.server.Exception.ExceptionEnum;
import com.capstone.server.Repository.TokenRepository;
import com.capstone.server.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, TokenService tokenService){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
    }

    public void join(JoinRequestDTO user){
        if (userRepository.existsByEmail(user.getEmail())){
            throw new ApiException(ExceptionEnum.EMAIL_DUPLICATE);
        }else if (userRepository.existsByNickname(user.getNickname())){
            throw new ApiException(ExceptionEnum.NICKNAME_DUPLICATE);
        }else if (userRepository.existsById(user.getId())){
            throw new ApiException(ExceptionEnum.ID_DUPLICATE);
        }
        //아이디, 이메일, 닉네임 중 중복된 값이 없으면 회원가입 가능함

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if(user.getRole() == ""){
            user.setRole("ROLE_USER");
        }

        userRepository.save(User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .role(user.getRole())
                .build());

        tokenService.TokenSave(TokenDTO.builder()
                .userId(user.getId())
                .token("")
                .status(false)
                .build());
    }
}

