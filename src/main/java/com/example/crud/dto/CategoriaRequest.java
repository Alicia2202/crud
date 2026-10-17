package com.example.crud.dto;

import jakarta.validation.constraints.*;

public record CategoriaRequest( //es lo mismo que hacer una clase normal pero con menos codigo
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    String nombre
) {}