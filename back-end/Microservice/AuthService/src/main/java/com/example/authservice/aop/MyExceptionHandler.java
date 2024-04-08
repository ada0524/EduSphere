package com.example.authservice.aop;

import com.example.authservice.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException e) {
        // Create a response structure with the necessary details
        // Adjust this according to your application's needs
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Incorrect credentials, please try again");
        response.put("error", e.getMessage());

        // Return a ResponseEntity
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
