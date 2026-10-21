package com.example.crud.dto;

public record TokenResponse(String token, String tipo, long expiraEnSegundos) {} //record crea get, set, equals, hashcode y toString automaticamente