package com.example.crud;

public class CategoriaDuplicadaException extends RuntimeException {
    public CategoriaDuplicadaException(String nombre) {
        super("Ya existe una categoría llamada '" + nombre + "'");
    }
}
