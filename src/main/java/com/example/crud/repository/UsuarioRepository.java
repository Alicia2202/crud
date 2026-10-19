package com.example.crud.repository;

import com.example.crud.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
//trabaja con la entidad usuario y su id es de tipo Long. El repositorio esta conectado con la base de datos
//es una interfaz que hereda de JpaRepository y le pasamos dos parametros: la clase con la que trabajamos (Usuario) y el tipo de dato de su id (Long)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { //hereda todos los metodos estandar: save, findById, findAll, delete, etc

    Optional<Usuario> findByUsername(String username); // Optional es para manejar el caso en que el usuario no exista (devuelve null)

    boolean existsByUsername(String username); //devuelve true si existe el usuario, false si no existe
}