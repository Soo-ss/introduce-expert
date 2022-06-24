package com.api.backend.infra.advice.exception;

public class CForbiddenWordException extends RuntimeException{
    public CForbiddenWordException() {
    }

    public CForbiddenWordException(String message) {
        super(message);
    }

    public CForbiddenWordException(String message, Throwable cause) {
        super(message, cause);
    }
}
