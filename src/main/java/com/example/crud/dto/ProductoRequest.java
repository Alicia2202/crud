package com.example.crud.dto;


import jakarta.validation.constraints.*;

public record ProductoRequest( // record es una forma corta de escribir una clase que solo guarda datos . Con estas pocas lineas java genera el constructor new ProductoRequest(n, p), metodos para leer los nombres y los precio (sin get ni set), porque un record no se puede modificar
// aqui estan las validaciones
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    String nombre,

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    Double precio,
    
    @NotNull(message = "La categoría es obligatoria") //--> aqui se produce la anidacion, se pasa el id como referencia (de la categoria) y Spring se encarga de convertirlo en Categoria.
    Long categoriaId
) {}
