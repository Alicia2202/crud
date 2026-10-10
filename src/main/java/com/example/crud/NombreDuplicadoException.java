package com.example.crud;
//es una excepcion con nombre propio . RuntimeException hace que no sea obligatorio declararla con throws y super(...) guarda el mensaje.

public class NombreDuplicadoException extends RuntimeException {
    public NombreDuplicadoException(String nombre) {
        super("Ya existe un producto llamado '" + nombre + "'");
    }
}