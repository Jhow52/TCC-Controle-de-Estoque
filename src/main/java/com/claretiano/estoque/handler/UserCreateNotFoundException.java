package com.claretiano.estoque.handler;

public class UserCreateNotFoundException extends RuntimeException {
    public UserCreateNotFoundException(String message) {
        super(message);
    }
}
