package com.example.crud;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.nombre());
        return categoria;
    }

    public static CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNombre());
    }
}