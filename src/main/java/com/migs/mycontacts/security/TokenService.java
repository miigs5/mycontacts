package com.migs.mycontacts.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.migs.mycontacts.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    @Value("${api.security.token.secret:senha-secreta-segura}")
    private String secret;

    @Value("${api.security.token.expirationMinutes:30}")
    private Long expirationMinutes;

    public String gerarToken(UsuarioEntity usuario) {
        try {
            return JWT.create()
                .withIssuer("API MyContacts")
                .withSubject(usuario.getUsername())
                .withExpiresAt(dataInstant())
                .sign(Algorithm.HMAC256(secret));
        }

        catch (JWTCreationException e) {
            throw new RuntimeException("Falha ao gerar token JWT.", e);
        }
    }

    public String getSubject(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("API MyContacts")
                .build()
                .verify(token)
                .getSubject();
        }

        catch (JWTVerificationException e) {
            System.out.println("Falha ao gerar token JWT." + e.getMessage());
            return null;
        }
    }

    private Instant dataInstant() {
        return Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES);
    }
}
