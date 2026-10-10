package com.example.crud; //indica en que paquete vive la clase y debe coincidir con la ruta com/example/crud

import jakarta.persistence.*; //trae las anotaciones de JPA (los objetos de la programacion orientada a objetos que se usan en springboot para mapear las tablas de la base de datos relacionales)
import jakarta.validation.constraints.*; //trae anotaciones de validacion de datos


@Entity // le dice a Spring que esta clase es una entidad, es decir, una tabla de la base de datos
public class Producto { //al arrancar spirng crea automáticamente la tabla producto

    @Id //anotacion o etiqueta para que JPA lo pueda leer
    @GeneratedValue(strategy = GenerationType.IDENTITY) //la base de datos genera el id automáticamente e incremental
    private Long id; //clave primaria, long es la clase e id es la variable

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio; // es Double (objeto) y no double (primitivo), porque permite valores nulos, de la otra manera se asigna 0.0 por defecto en valores nulos

    public Producto() {} //constructor vacío, obligatorio para JPA para crear objetos al leer la base de datos y Spring también al convertir el JSON que recibes.

    public Long getId() { return id; } //GETTERS, metodo para leer los atributos privados 
    public void setId(Long id) { this.id = id; } //SETTERS, metodo para modificar los atributos privados . Spring usa Getters y setterr para convertis el objeto a JSON y al revés.
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}