package com.sandro.exception;

public class LectorNotFoundException extends RuntimeException {
    public LectorNotFoundException(String message) {
        super(message);
    }
}
