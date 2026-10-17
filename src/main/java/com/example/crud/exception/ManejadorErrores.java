package com.example.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Es una clase que vigila a todos los controladores y atrapa sus errores.
                      // Indica que esta clase manejará errores en toda la aplicación
public class ManejadorErrores {
    // Indica que este método manejará errores de validación
    @ExceptionHandler(MethodArgumentNotValidException.class) // captura los errores de validación @valid
    @ResponseStatus(HttpStatus.BAD_REQUEST) // devuelve 400 Bad Request
    public Map<String, String> errorValidacion(MethodArgumentNotValidException ex) { // objeto que contiene los errores
        Map<String, String> errores = new HashMap<>(); // crea un mapa de errores
        ex.getBindingResult().getFieldErrors() // recorre los errores de validación
                .forEach(e -> errores.put(e.getField(), e.getDefaultMessage())); // agrega cada error al mapa
        return errores; // devuelve el mapa de errores
    }

    @ExceptionHandler(NombreDuplicadoException.class) // captura los errores de validación @valid
    @ResponseStatus(HttpStatus.CONFLICT) // devuelve 409 Conflict
    public Map<String, String> nombreDuplicado(NombreDuplicadoException ex) { // objeto que contiene los errores
        return Map.of("error", ex.getMessage()); // devuelve el mapa de errores
    }

    @ExceptionHandler(CategoriaNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> categoriaNoEncontrada(CategoriaNoEncontradaException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(CategoriaConProductosException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> categoriaConProductos(CategoriaConProductosException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(CategoriaDuplicadaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> categoriaDuplicada(CategoriaDuplicadaException ex) {
        return Map.of("error", ex.getMessage());
    }
}