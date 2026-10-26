package com.example.crud.dto;

import jakarta.validation.constraints.*;

public record RegistroRequest(

   @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$",
             message = "El usuario solo puede tener letras, números, puntos, guiones y guiones bajos")
    String username,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String password

) {}