package com.example.crud.exception;

public class TokenGoogleInvalidoException extends RuntimeException {
    public TokenGoogleInvalidoException() {
        super("Token de Google no válido");
    }
}