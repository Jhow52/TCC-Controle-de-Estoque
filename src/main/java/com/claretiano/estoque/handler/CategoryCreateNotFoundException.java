package com.claretiano.estoque.handler;

public class CategoryCreateNotFoundException extends RuntimeException {
    public CategoryCreateNotFoundException(String message) {
        super(message);
    }
}
