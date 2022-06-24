package com.api.backend.infra.advice.exception;

public class CAccountNotFoundException extends RuntimeException{
    public CAccountNotFoundException() {
    }

    public CAccountNotFoundException(String message) {
        super(message);
    }

    public CAccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
