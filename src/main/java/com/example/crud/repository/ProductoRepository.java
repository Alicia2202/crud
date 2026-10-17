package com.example.crud.repository;

import com.example.crud.entity.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//trabaja con la entidad producto y su id es de tipo Long
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id); // el segundo metodo sirve para actualizar. Si edito el producto1 y mantienes su nombre, no debe contar como duplicado consigo mismo.
    
    List<Producto> findByCategoriaId(Long categoriaId);//devuelve los productos de una categoría
    boolean existsByCategoriaId(Long categoriaId);
}

// el acceso a datos

//interfaz vacía y aun asi funciona. AL extender JpaRepository, hereda todos los metodos del CRUD y por eso no necesitamos definir nada,Spring Boot se encarga de todo.

