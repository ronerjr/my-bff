package com.roner.bff.exception;

public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Exception exception) {
        super(message, exception);
    }
}