package com.example.bankapplication.exception;

public class ClientUnderageException extends RuntimeException {
    public ClientUnderageException(String message) {
        super(message);
    }
}
