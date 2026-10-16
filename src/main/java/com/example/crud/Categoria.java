package com.example.crud;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "categoria") // Indica que esta categoría tiene muchos productos, y el mappedBy indica que la relación está controlada por el campo "categoria" de la clase Producto.
    private List<Producto> productos = new ArrayList<>(); // al leer una categoria JPA puede rellenar esta lista con sus productos, empieza con una lista vacia

    public Categoria() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Producto> getProductos() { return productos; }
}