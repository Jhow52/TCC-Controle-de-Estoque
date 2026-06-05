package com.claretiano.estoque.handler;

public class ProductCreateNotFoundException extends RuntimeException {
    public ProductCreateNotFoundException(String message) {
        super(message);
    }
}
