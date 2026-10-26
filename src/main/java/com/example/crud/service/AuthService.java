package com.example.crud.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.example.crud.dto.GoogleLoginRequest;
import com.example.crud.dto.LoginRequest;
import com.example.crud.dto.RegistroRequest;
import com.example.crud.dto.TokenResponse;
import com.example.crud.dto.UsuarioResponse;
import com.example.crud.entity.Rol;
import com.example.crud.entity.Usuario;
import com.example.crud.exception.CredencialesInvalidasException;
import com.example.crud.exception.TokenGoogleInvalidoException;
import com.example.crud.exception.UsuarioDuplicadoException;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.security.GoogleTokenVerifier;
import com.example.crud.security.TokenService; //Importamos TokenService que esta en otro paquete


@Service
public class AuthService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final GoogleTokenVerifier googleVerifier;

    public AuthService(UsuarioRepository repo, PasswordEncoder passwordEncoder, TokenService tokenService, GoogleTokenVerifier googleVerifier) { //Inyeccion de dependencias mediante constructor
        this.repo = repo; //inyeccion de dependencias mediante constructor
        this.passwordEncoder = passwordEncoder; //inyeccion de dependencias mediante constructor
        this.tokenService = tokenService; //inyeccion de dependencias mediante constructor
        this.googleVerifier = googleVerifier; //inyeccion de dependencias mediante constructor
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
    public TokenResponse loginConGoogle(GoogleLoginRequest request) {
    Jwt google = googleVerifier.verificar(request.idToken());

    String email = google.getClaimAsString("email"); // lee el email del playload
    Boolean verificado = google.getClaimAsBoolean("email_verified"); // verifica que el email sea verificado
    if (email == null || !Boolean.TRUE.equals(verificado)) { // Si el email no es verificado, lanza una excepción
        throw new TokenGoogleInvalidoException();
    }

    Usuario usuario = repo.findByUsername(email).orElseGet(() -> { // Si el usuario no existe, lo crea
        Usuario nuevo = new Usuario();
        nuevo.setUsername(email); // Establece el email como nombre de usuario
        nuevo.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // Genera una contraseña aleatoria
        nuevo.setRol(Rol.USER); // Establece el rol por defecto como USER
        return repo.save(nuevo);
    });

    String token = tokenService.generar(usuario);
    return new TokenResponse(token, "Bearer", tokenService.getExpiracionSegundos());
}

}