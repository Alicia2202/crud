package com.example.crud.security;

import com.example.crud.exception.TokenGoogleInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GoogleTokenVerifier {

    private static final String CLAVES_GOOGLE = "https://www.googleapis.com/oauth2/v3/certs"; // URL donde Google publica sus claves públicas para firmar tokens
    private static final Set<String> EMISORES_GOOGLE = // Emisores válidos de Google // lo guardamos para tenerlo a mano ya que no lo necesita mi API solo postman
            Set.of("https://accounts.google.com", "accounts.google.com"); 

    private final JwtDecoder decoder; // Decodificador de JWT , en este caso un decodificador de JWT de Google aqui guardamos el verificador 

    public GoogleTokenVerifier(@Value("${google.client-id}") String clientId) { //es el constructor que crea el objeto Decoder e inyecta el clientId//clientId es el id de la aplicacion que le dimos de alta en google cloud
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(CLAVES_GOOGLE).build(); // NimbusJwtDecoder es una implementacion de JwtDecoder que se encarga de decodificar el token
         //.withJwkSetUri descarga las claves publicas de google desde esa direccion para comprobar las firmas. Tu API necesita conexion a internet para poder verificar el token

        OAuth2TokenValidator<Jwt> emisor = jwt -> //emisor comprueba que el iss (quien lo expide) es google, google usa con https:// y sin ella y se aceptan las dos.
                EMISORES_GOOGLE.contains(jwt.getClaimAsString(JwtClaimNames.ISS))
                        ? OAuth2TokenValidatorResult.success()
                        : OAuth2TokenValidatorResult.failure(
                                new OAuth2Error("invalid_token", "No lo ha emitido Google", null));

        OAuth2TokenValidator<Jwt> audiencia = jwt -> // audiencia comprueba que el aud es mi ClienteID, sin ella un token que emitio google me lo podria gastar yo misma
                jwt.getAudience().contains(clientId)
                        ? OAuth2TokenValidatorResult.success()
                        : OAuth2TokenValidatorResult.failure(
                                new OAuth2Error("invalid_token", "No es para esta aplicación", null));

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>( // DelegatingOAuth2TokenValidator es una clase que permite validar el token con varios validadores
                new JwtTimestampValidator(), emisor, audiencia)); //JwtTimestampValidator valida que el token no este expirado

        this.decoder = decoder; // guardamos el decoder en el campo decoder para usarlo como atributo en cada verificacion que es cuando se inyecta el clientId
    }

    public Jwt verificar(String idToken) { //metodo que verifica el token y devuelve el Jwt si es valido
        try {
            return decoder.decode(idToken); //decodifica el token
        } catch (JwtException e) {
            throw new TokenGoogleInvalidoException(); //lanza una excepcion si el token no es valido
        }
    }
}