package com.claretiano.estoque.handler;

public class InventoryNotFound extends RuntimeException {
    public InventoryNotFound(String message) {
        super(message);
    }
}
