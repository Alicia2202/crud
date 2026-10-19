package com.example.crud.entity; //indica en que paquete vive la clase y debe coincidir con la ruta com/example/crud

import jakarta.persistence.*; //trae las anotaciones de JPA (los objetos de la programacion orientada a objetos que se usan en springboot para mapear las tablas de la base de datos relacionales)
//import jakarta.validation.constraints.*; //trae anotaciones de validacion de datos
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // le dice a Spring que esta clase es una entidad, es decir, una tabla de la base de datos
@Getter //GETTERS, metodo para leer los atributos privados tanto para id como para nombre, precio y categoria
@Setter //SETTERS, metodo para modificar los atributos privados tanto para id como para nombre, precio y categoria.

public class Producto { //al arrancar spirng crea automáticamente la tabla producto

    @Id //anotacion o etiqueta para que JPA lo pueda leer
    @GeneratedValue(strategy = GenerationType.IDENTITY) //la base de datos genera el id automáticamente e incremental
    private Long id; //clave primaria, long es la clase e id es la variable
    
    private String nombre;
    private Double precio; // es Double (objeto) y no double (primitivo), porque permite valores nulos, de la otra manera se asigna 0.0 por defecto en valores nulos

    @ManyToOne //añadimos la relacion de categoria a producto
    @JoinColumn(name = "categoria_id")
    private Categoria categoria; //agregamos el campo categoria

    public Producto() {}
    

}