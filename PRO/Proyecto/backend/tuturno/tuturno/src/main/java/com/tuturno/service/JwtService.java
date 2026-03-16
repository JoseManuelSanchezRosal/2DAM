package com.tuturno.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // Leemos la variable desde el application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // Método auxiliar para generar la llave y no repetir código
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Long idUsuario) {
        return Jwts
                .builder()
                .claim("id-usuario", idUsuario)
                // El token expira en 30 minutos
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token) {
        JwtParser jwtParser = Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build();

        try {
            jwtParser.parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}