package com.example.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
//no se usa @Data porque al tener una lista no queremos que se llene de getters y setters infiniteamente haria un bucle porque categoria.toString imprimiria sus productos y cada producto imprimiria su categoria.toString
@Entity //al arrancar spirng crea automáticamente la tabla categoria
@Getter //GETTERS, metodo para leer los atributos privados tanto para id como para nombre y categoria
@Setter //SETTERS, metodo para modificar los atributos privados tanto para id como para nombre y categoria. 
public class Categoria {

    @Id //anotacion o etiqueta para que JPA lo pueda leer
    @GeneratedValue(strategy = GenerationType.IDENTITY) //la base de datos genera el id automáticamente e incremental
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "categoria") // Indica que esta categoría tiene muchos productos, y el mappedBy indica que la relación está controlada por el campo "categoria" de la clase Producto. El dueño es el que tiene @ManyToOne que tiene la columna categoria_id
    private List<Producto> productos = new ArrayList<>(); // al leer una categoria JPA puede rellenar esta lista con sus productos, empieza con una lista vacia

    public Categoria() {}
}