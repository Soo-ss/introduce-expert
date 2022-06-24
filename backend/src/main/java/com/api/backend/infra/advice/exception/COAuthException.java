package com.api.backend.infra.advice.exception;

public class COAuthException extends RuntimeException{
    public COAuthException() {
    }

    public COAuthException(String message) {
        super(message);
    }

    public COAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
