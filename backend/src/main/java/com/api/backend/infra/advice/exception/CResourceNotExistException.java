package com.api.backend.infra.advice.exception;

public class CResourceNotExistException extends RuntimeException{
    public CResourceNotExistException() {
    }

    public CResourceNotExistException(String message) {
        super(message);
    }

    public CResourceNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
