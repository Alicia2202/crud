package com.example.crud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> { //trabaja con la entidad producto y su id es de tipo Long
}

// el acceso a datos

//interfaz vacía y aun asi funciona. AL extender JpaRepository, hereda todos los metodos del CRUD y por eso no necesitamos definir nada,Spring Boot se encarga de todo.
// 