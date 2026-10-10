package com.example.crud;

// DTO de salida: lo que el cliente ve en el JSON de respuesta
public record ProductoResponse(
    Long id,
    String nombre,
    Double precio
) {}
