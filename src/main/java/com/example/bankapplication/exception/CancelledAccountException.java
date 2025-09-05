package com.example.bankapplication.exception;

public class CancelledAccountException extends RuntimeException {
    public CancelledAccountException(String message) {
        super(message);
    }
}
