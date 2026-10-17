package com.example.crud.mapper;

import com.example.crud.entity.Producto;
import com.example.crud.entity.Categoria;
import com.example.crud.dto.ProductoRequest;
import com.example.crud.dto.ProductoResponse;
import com.example.crud.dto.CategoriaResponse;
// sirve para transformar Request a Entity (producto) y viceversa. Es decir, alguien que convierta el DTO  en entidad y la entidad en DTO.
public class ProductoMapper {

    public static Producto toEntity(ProductoRequest request, Categoria categoria) { // static se usa sin crear un objeto ProductoMaper.toResponse(p). No guardan, solo transforman.
        //toEntity no asigna id. asi que la base de datos siempre pondrá uno nuevo.
        Producto producto = new Producto();
        producto.setNombre(request.nombre());
        producto.setPrecio(request.precio());
        producto.setCategoria(categoria);
        return producto;
    }

    public static ProductoResponse toResponse(Producto producto) { // toResponse es lo contrario: toma la entidad y la convierte en un DTO. Lo que ve el cliente
        CategoriaResponse categoria = producto.getCategoria() == null
                ? null
                : CategoriaMapper.toResponse(producto.getCategoria()); //si tiene categoria conviertela en DTO, si no devuelve null

    
        return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getPrecio(),
            categoria
        );
    }
}
