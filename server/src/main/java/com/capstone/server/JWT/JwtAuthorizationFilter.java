package com.capstone.server.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.capstone.server.Auth.PrincipalDetails;
import com.capstone.server.Domain.User;
import com.capstone.server.Exception.ApiException;
import com.capstone.server.Exception.ExceptionEnum;
import com.capstone.server.Repository.TokenRepository;
import com.capstone.server.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenRepository tokenRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, ApiException {
        String JwtHeader = request.getHeader(JwtProperties.ACCESS_HEADER_STRING);
        if (JwtHeader == null || !JwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            request.setAttribute("exception", ExceptionEnum.INVALID_TOKEN.getMessage());
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(JwtProperties.ACCESS_HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        if (request.getRequestURI() != "/users/authorize" && !tokenRepository.existsByToken(jwtToken)) { //check token DB while it isn't login process-> if exists : logout status.
            try {
                String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                        .build()
                        .verify(jwtToken)
                        .getClaim("id")
                        .asString();
                if (username != null) {
                    Optional<User> user = userRepository.findById(username);
                    if (user.isEmpty()) {
                        request.setAttribute("exception", ExceptionEnum.INVALID_TOKEN.getMessage());
                    } else {
                        PrincipalDetails principalDetails = new PrincipalDetails(user.get());
                        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    request.setAttribute("exception", ExceptionEnum.INVALID_TOKEN.getMessage());
                }
            } catch (TokenExpiredException e) {
                request.setAttribute("exception", ExceptionEnum.EXPIRED_TOKEN.getMessage());
            } catch (JWTDecodeException e) {
                request.setAttribute("exception", ExceptionEnum.INVALID_TOKEN.getMessage());
            } catch (Exception e) {
                request.setAttribute("exception", ExceptionEnum.INVALID_TOKEN.getMessage());
            }
        } else {
            request.setAttribute("exception", ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode());
        }
        chain.doFilter(request, response);
    }

}
