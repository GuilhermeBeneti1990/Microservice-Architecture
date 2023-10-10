package com.beneti.productapi.config.exceptions;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message) {
        super(message);
    }

}
