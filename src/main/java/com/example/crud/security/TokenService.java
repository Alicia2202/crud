package com.example.crud.security;

import com.example.crud.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm; //indica que se forma con HMAC-SHA256
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
//tokenService se encarga de crear el token y devolverlo
@Service //indica que Spring debe crear y gestionar un objeto (en este caso, un codificador de contraseñas)
public class TokenService { //clase que se encarga de crear el token

    private final JwtEncoder encoder; //crea el token. viene de SecurityConfig
    private final long expiracionMinutos; //expiracion del token. viene de application.properties

    public TokenService(JwtEncoder encoder, //jwtEncoder permite crear tokens firmados con la clave secreta
                        @Value("${jwt.expiracion-minutos}") long expiracionMinutos) { //lee la propiedad jwt.secret del archivo application.properties
        this.encoder = encoder; //guarda el encoder
        this.expiracionMinutos = expiracionMinutos; //guarda la expiracion
    }

    public String generar(Usuario usuario) { //crea el token
        Instant ahora = Instant.now(); //crea el momento actual

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("crud-api") //quien emite el token
                .issuedAt(ahora) //cuando se emite el token
                .expiresAt(ahora.plus(expiracionMinutos, ChronoUnit.MINUTES)) //cuando expira el token
                .subject(usuario.getUsername()) //a quien pertenece el token
                .claim("roles", List.of("ROLE_" + usuario.getRol().name())) //roles del usuario
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public long getExpiracionSegundos() {
        return expiracionMinutos * 60;
    }
}