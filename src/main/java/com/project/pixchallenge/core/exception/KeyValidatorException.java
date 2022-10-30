package com.project.pixchallenge.core.exception;

public class KeyValidatorException extends RuntimeException {

    public KeyValidatorException(String message) {
        super(message);
    }

    public KeyValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyValidatorException(Throwable cause) {
        super(cause);
    }

    public KeyValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
