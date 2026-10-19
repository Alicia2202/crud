package com.example.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) //guarda el rol como texto ("ADMIN", "USER") en lugar de numeros (0, 1)
    @Column(nullable = false)
    private Rol rol;

    public Usuario() {}
}