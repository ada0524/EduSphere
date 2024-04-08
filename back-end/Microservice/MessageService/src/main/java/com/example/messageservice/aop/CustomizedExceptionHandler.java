package com.example.messageservice.aop;


import com.example.messageservice.dto.DataResponse;
import com.example.messageservice.exceptions.UnauthenticatedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<DataResponse> handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
        return new ResponseEntity<>(DataResponse.builder().success(false).message("Message ID Does Not Exist").build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<DataResponse> handleUnauthenticatedExceptionException(UnauthenticatedException e, WebRequest request) {
        return new ResponseEntity<>(DataResponse.builder().success(false).message(e.getMessage()).build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<DataResponse> handleValidationException(ValidationException e, WebRequest request) {
        return new ResponseEntity<>(DataResponse.builder().success(false).message(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(DataResponse.builder().success(false).message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class) // Catch-all for any exception
    public ResponseEntity<DataResponse> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<>(DataResponse.builder().success(false).message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
