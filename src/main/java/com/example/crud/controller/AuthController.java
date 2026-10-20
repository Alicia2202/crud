package com.example.crud.controller;

import com.example.crud.dto.RegistroRequest;
import com.example.crud.dto.UsuarioResponse;
import com.example.crud.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/registro")
    public UsuarioResponse registrar(@Valid @RequestBody RegistroRequest request) {
        return service.registrar(request);
    }

    @GetMapping("/yo")
    public Map<String, Object> yo(Authentication auth) {
        return Map.of(
            "username", auth.getName(),
            "roles", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
    }
}