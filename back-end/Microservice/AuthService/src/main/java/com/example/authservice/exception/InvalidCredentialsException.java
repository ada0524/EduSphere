package com.example.authservice.exception;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException(String s){
        super(s);
    }
}
