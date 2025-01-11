package com.sandro.exception;

public class MaxStudentsReachedException extends RuntimeException {
    public MaxStudentsReachedException(String message) {
        super(message);
    }
}
