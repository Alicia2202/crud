package com.example.crud;

public class CategoriaConProductosException extends RuntimeException {
    public CategoriaConProductosException(Long id) {
        super("La categoría " + id + " tiene productos y no se puede borrar");
    }
}