package com.example.crud.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.dto.LoginRequest;
import com.example.crud.dto.RegistroRequest;
import com.example.crud.dto.TokenResponse;
import com.example.crud.dto.UsuarioResponse;
import com.example.crud.entity.Rol;
import com.example.crud.entity.Usuario;
import com.example.crud.exception.CredencialesInvalidasException;
import com.example.crud.exception.UsuarioDuplicadoException;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.security.TokenService; //Importamos TokenService que esta en otro paquete

@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UsuarioRepository repo, PasswordEncoder passwordEncoder, TokenService tokenService) { //Inyeccion de dependencias mediante constructor
        this.repo = repo; //inyeccion de dependencias mediante constructor
        this.passwordEncoder = passwordEncoder; //inyeccion de dependencias mediante constructor
        this.tokenService = tokenService; //inyeccion de dependencias mediante constructor
    } 

    public UsuarioResponse registrar(RegistroRequest request) {  // Metodo para registrar un nuevo usuario
        if (repo.existsByUsername(request.username())) {
            throw new UsuarioDuplicadoException(request.username());
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncoder.encode(request.password())); // Codifica la contraseña antes de guardarla. Spring inyecta el BCryptPasswordEncoder bean
        usuario.setRol(Rol.USER); // Establece el rol por defecto como USER

        Usuario guardado = repo.save(usuario);
        return new UsuarioResponse(guardado.getId(), guardado.getUsername(), guardado.getRol().name()); // Retorna el usuario registrado
    }

    public TokenResponse login(LoginRequest request) {
        Usuario usuario = repo.findByUsername(request.username()) // Buscamos el usuario por nombre
                .filter(u -> passwordEncoder.matches(request.password(), u.getPassword())) // Comparamos la contraseña hasheada
                .orElseThrow(CredencialesInvalidasException::new); // Si no existe o la contraseña es incorrecta, lanzamos una excepción

        String token = tokenService.generar(usuario);
        return new TokenResponse(token, "Bearer", tokenService.getExpiracionSegundos());
    }
}