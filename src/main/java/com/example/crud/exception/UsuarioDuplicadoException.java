package com.example.crud.exception;

public class UsuarioDuplicadoException extends RuntimeException {
    public UsuarioDuplicadoException(String username) {
        super("Ya existe un usuario llamado '" + username + "'");
    }
}