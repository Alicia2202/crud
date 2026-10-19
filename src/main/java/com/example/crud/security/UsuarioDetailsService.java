package com.example.crud.security;

import com.example.crud.entity.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService { // esta clase cumple el contrato que Spring Security espera para cargar los usuarios. Spring la detecta y la usa automaticamente.

    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override //debes implementar este metodo para cumplir con la interfaz UserDetailsService. 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //toma el username de la peticion (p ej. en el login),
        Usuario usuario = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol().name())
                .build(); //convierte al usuario de nuestra aplicacion en un UserDetails, que es el formato estandar que entiende Spring Security.
    }
}

//no compruebo la contraseña. Solo entrego el usuario con su hash para que Spring Security lo compare 