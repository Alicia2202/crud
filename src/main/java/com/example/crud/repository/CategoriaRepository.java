package com.example.crud.repository;

import com.example.crud.entity.Categoria;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id); // el segundo metodo sirve para actualizar. Si edito el producto1 y mantienes su nombre, no debe contar como duplicado consigo mismo.
    
    
}
