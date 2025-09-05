package com.example.bankapplication.exception;

public class NonZeroBalanceAccountDeletionException extends RuntimeException {
    public NonZeroBalanceAccountDeletionException(String message) {
        super(message);
    }
}
