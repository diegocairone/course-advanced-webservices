package com.cairone.error;

public abstract class AppBaseException extends RuntimeException {

    protected AppBaseException(String message) {
        super(message);
    }

    protected AppBaseException(String message, Object... args) {
        super(String.format(message, args));
    }

    protected AppBaseException(Throwable cause, String message) {
        super(message, cause);
    }

    protected AppBaseException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }
}
