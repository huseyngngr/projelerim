package com.healthylife.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final transient Object[] args;

    protected BaseException(String message, HttpStatus status, String code, Object... args) {
        super(message);
        this.status = status;
        this.code = code;
        this.args = args;
    }

    protected BaseException(String message, Throwable cause, HttpStatus status, String code, Object... args) {
        super(message, cause);
        this.status = status;
        this.code = code;
        this.args = args;
    }
} 