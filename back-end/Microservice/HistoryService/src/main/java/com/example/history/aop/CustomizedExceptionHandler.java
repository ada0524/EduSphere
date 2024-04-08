package com.example.history.aop;


import com.example.history.dto.DataResponse;
import com.example.history.exceptions.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<DataResponse> handleUnauthenticatedExceptionException(UnauthenticatedException e, WebRequest request) {
        return new ResponseEntity<>(DataResponse.builder().success(false).message(e.getMessage()).build(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class) // Catch-all for any exception
    public ResponseEntity<DataResponse> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<>(DataResponse.builder().success(false).message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
