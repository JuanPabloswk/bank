package com.example.bankapplication.exception;

public class ClientHasAccountsException extends RuntimeException {
    public ClientHasAccountsException(String message) {
        super(message);
    }
}
