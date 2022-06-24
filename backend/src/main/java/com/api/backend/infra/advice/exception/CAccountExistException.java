package com.api.backend.infra.advice.exception;

public class CAccountExistException extends RuntimeException{
    public CAccountExistException() {
    }

    public CAccountExistException(String message) {
        super(message);
    }

    public CAccountExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
