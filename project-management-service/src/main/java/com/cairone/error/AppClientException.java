package com.cairone.error;

public class AppClientException extends RuntimeException {

    public AppClientException(String message) {
        super(message);
    }

    public AppClientException(String message, Object... args) {
        super(String.format(message, args));
    }
}
