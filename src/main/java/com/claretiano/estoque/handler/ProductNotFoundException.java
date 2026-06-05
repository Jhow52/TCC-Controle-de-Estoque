package com.claretiano.estoque.handler;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Produto com o ID: " + id + " não foi encontrado");
    }
}
