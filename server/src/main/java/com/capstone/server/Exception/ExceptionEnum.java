package com.capstone.server.Exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {

    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, 403, "unauthorized"),
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, 404, "bad request"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, 400, "email duplicated"),
    NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, 400, "nickname duplicated"),
    ID_DUPLICATE(HttpStatus.BAD_REQUEST, 400, "id duplicated"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "internal server error"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "expired token"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 401, "invalid token"),

    TOKEN_ERROR(HttpStatus.BAD_REQUEST, 400, "token error"),
            ;


    private final HttpStatus status;
    private final int code;
    private String message;

    ExceptionEnum(HttpStatus status, int code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
