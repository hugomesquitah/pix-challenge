package com.project.pixchallenge.core.exception;

public class KeyValidatorException extends RuntimeException {

    public KeyValidatorException(String message) {
        super(message);
    }

    public KeyValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

}
