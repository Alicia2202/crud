package com.example.crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean //Bean indica que Spring debe crear y gestionar un objeto (en este caso, un codificador de contraseñas)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //es el algoritmo de hash que se usa para codificar las contraseñas
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) //desactiva la protección CSRF, necesaria para el uso de tokens, proteccion para webs que usan cookies en el navegador.
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //desactiva las sesiones tradicionales de http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/auth/registro").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                .requestMatchers("/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}