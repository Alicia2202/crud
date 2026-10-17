package com.example.crud.dto;

// DTO de salida: lo que el cliente ve en el JSON de respuesta
public record ProductoResponse(
    Long id,
    String nombre,
    Double precio, 
    CategoriaResponse categoria //la categoria va anidada dentro del producto , es un objeto anidado dentro de otro. Hacia dentro solo de envia el id, que es mas sencillo para el cliente. Hacia fuera se devuelve el objeto mas completo que le da mas informacion.
) {} //si en un futuro la categoria tiene mas campos (por ejemplo una lista de productos), no es necesario modificar el request ni la base de datos.
