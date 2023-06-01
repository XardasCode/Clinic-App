package com.clinic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorList {
    USER_NOT_FOUND(1, HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(2, HttpStatus.CONFLICT),
    USER_NOT_CREATED(3, HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_UPDATED(4, HttpStatus.INTERNAL_SERVER_ERROR),
    VISIT_NOT_FOUND(5, HttpStatus.NOT_FOUND),
    VISIT_ALREADY_EXISTS(6, HttpStatus.CONFLICT),
    VISIT_NOT_CREATED(7, HttpStatus.INTERNAL_SERVER_ERROR),
    VISIT_NOT_UPDATED(8, HttpStatus.INTERNAL_SERVER_ERROR),
    SERVER_ERROR(9, HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(10, HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(11, HttpStatus.NOT_FOUND),
    STATUS_NOT_FOUND(12, HttpStatus.NOT_FOUND),
    WRONG_PASSWORD(13, HttpStatus.BAD_REQUEST), BAD_REQUEST(14, HttpStatus.BAD_REQUEST);

    private final int errorCode;
    private final HttpStatus httpStatusCode;

}
