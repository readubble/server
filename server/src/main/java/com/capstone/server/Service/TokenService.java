package com.capstone.server.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.capstone.server.DTO.TokenDTO;
import com.capstone.server.Domain.Token;
import com.capstone.server.Exception.ApiException;
import com.capstone.server.Exception.ExceptionEnum;
import com.capstone.server.JWT.JwtProperties;
import com.capstone.server.Repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }

    public void saveToken(TokenDTO tokenDTO){
        tokenRepository.save(tokenDTO.toEntity());
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
            tokenRepository.save(tokenDTO.toEntity());
        } else{
            throw new ApiException(ExceptionEnum.RUNTIME_EXCEPTION); //bad request
        }
    }

    public boolean isTokenAvailable(String token){
        try{
            int expires = JWT.decode(token)
                    .getExpiresAt()
                    .compareTo(new Date(System.currentTimeMillis()));
            if (expires>0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e){ //토큰 자체가 잘못된 경우
            throw new ApiException(ExceptionEnum.TOKEN_ERROR);
        }
    }
    private String createAccessToken(String userId){
        String jwtAccessToken = JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME))
                .withClaim("id", userId)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return jwtAccessToken;
    }
    public String renewToken(String refreshToken, String userId) throws ApiException {
        if(getUsername(refreshToken).equals(userId) && tokenRepository.existsByToken(refreshToken)){
            if (!isTokenAvailable(refreshToken)) { //refresh token이 만료 -> 재로그인 필요
                throw new ApiException(ExceptionEnum.EXPIRED_TOKEN);
            } else{
                String accessToken = createAccessToken(userId);
                return accessToken;
            }
        } else{ //유효하지 않은 토큰
            throw new ApiException(ExceptionEnum.TOKEN_ERROR);
        }
    }

    public void updateTokenStatus(String userId, String accessToken){
        Optional<Token> token = tokenRepository.findById(userId);
        Token newToken = Token.builder()
                .userId(token.get().getUserId())
                .token(accessToken)
                .status(false).build();
        tokenRepository.save(newToken);
    }
}
