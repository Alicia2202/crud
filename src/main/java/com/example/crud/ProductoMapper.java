package com.example.crud;
// sirve para transformar Request a Entity (producto) y viceversa. Es decir, alguien que convierta el DTO  en entidad y la entidad en DTO.
public class ProductoMapper {

    public static Producto toEntity(ProductoRequest request) { // static se usa sin crear un objeto ProductoMaper.toResponse(p). No guardan, solo transforman.
        //toEntity no asigna id. asi que la base de datos siempre pondrá uno nuevo.
        Producto producto = new Producto();
        producto.setNombre(request.nombre());
        producto.setPrecio(request.precio());
        return producto;
    }

    public static ProductoResponse toResponse(Producto producto) { // toResponse es lo contrario: toma la entidad y la convierte en un DTO. Lo que ve el cliente
        return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getPrecio()
        );
    }
}