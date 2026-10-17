package com.example.crud.exception;

public class CategoriaConProductosException extends RuntimeException {
    public CategoriaConProductosException(Long id) {
        super("La categoría " + id + " tiene productos y no se puede borrar");
    }
}