package com.claretiano.estoque.handler;

public class CategoryEmUsoException extends RuntimeException {
    public CategoryEmUsoException(String message) {
        super(message);
    }
}
