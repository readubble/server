package com.capstone.server.Auth;

import com.capstone.server.Exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String exception = request.getAttribute("exception").toString();
        if (exception == null || exception.equals(ExceptionEnum.RUNTIME_EXCEPTION.getMessage())) {
            setResponse(response, ExceptionEnum.RUNTIME_EXCEPTION);
        } else if (exception.equals(Integer.toString(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode()))) {
            setResponse(response, ExceptionEnum.ACCESS_DENIED_EXCEPTION);
        } else if (exception.equals(ExceptionEnum.EXPIRED_TOKEN.getMessage())) {
            setResponse(response, ExceptionEnum.EXPIRED_TOKEN);
        } else if (exception.equals(ExceptionEnum.INVALID_TOKEN.getMessage())) {
            setResponse(response, ExceptionEnum.INVALID_TOKEN);
        }
    }
    private void setResponse(HttpServletResponse response, ExceptionEnum exception) throws IOException {
        response.setContentType("application/Json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{ \"code\" : \"" + exception.getCode()
                + "\", \"message\" : \"" + exception.getMessage() + "\"}");
    }
}
