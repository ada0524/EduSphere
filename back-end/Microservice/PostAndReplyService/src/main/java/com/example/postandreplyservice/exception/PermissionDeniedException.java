package com.example.postandreplyservice.exception;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException (String message) {
        super(message);
    }
}
