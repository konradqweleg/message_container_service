package com.example.messagecontainer.exception.user_service;

public class InvalidUserRequestException extends RuntimeException {
    public InvalidUserRequestException(String message) {
        super(message);
    }

}

