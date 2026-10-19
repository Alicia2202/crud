package com.example.crud.dto;

public record UsuarioResponse(Long id, String username, String rol) {}

//es una clase que contiene la informacion que se envia al cliente cuando solicita los datos de un usuario