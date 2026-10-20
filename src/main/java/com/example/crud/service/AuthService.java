package com.example.crud.service;

import com.example.crud.dto.RegistroRequest;
import com.example.crud.dto.UsuarioResponse;
import com.example.crud.entity.Rol;
import com.example.crud.entity.Usuario;
import com.example.crud.exception.UsuarioDuplicadoException;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    } // Inyeccion de dependencias mediante constructor

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
}