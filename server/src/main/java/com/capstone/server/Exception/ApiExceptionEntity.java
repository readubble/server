package com.capstone.server.Exception;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiExceptionEntity {

    private int code; //error code
    private String message; //error message

    @Builder
    public ApiExceptionEntity(HttpStatus status, int code, String message) {
        this.code = code;
        this.message = message;
    }
}
