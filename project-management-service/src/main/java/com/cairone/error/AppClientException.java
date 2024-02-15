package com.cairone.error;

public class AppClientException extends AppBaseException {

    public AppClientException(String message) {
        super(message);
    }

    public AppClientException(String message, Object... args) {
        super(message, args);
    }

    public AppClientException(Throwable cause, String message) {
        super(cause, message);
    }

    public AppClientException(Throwable cause, String message, Object... args) {
        super(cause, message, args);
    }
}
