package com.cine.entradas.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * SERVICIO JWT (JSON WEB TOKEN)
 * -----------------------------
 * Se encarga de la criptografía: Generar tokens firmados y validar tokens entrantes.
 * No interactúa con la base de datos, es pura lógica matemática/criptográfica.
 */
@Service
public class JwtService {

    // La llave secreta para firmar digitalmente. Si alguien tiene esto, puede falsificar tokens.
    // En producción, esto iría en application.properties o variables de entorno.
    private static final String SECRET_KEY = "c1n3S3cr3t0SuP3rS3gur0P4r4ElProy3ct0D3SpriNgBo0t2024";

    /**
     * Genera un token simple solo con los datos del usuario.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Construye el Token JWT con:
     * - Claims (datos extra)
     * - Subject (email del usuario)
     * - Fecha de emisión y expiración (24 horas)
     * - Firma digital (HMAC con SHA-256)
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 día de validez
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida el token comprobando:
     * 1. Que el usuario del token coincide con el usuario real.
     * 2. Que el token no ha caducado.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // --- Métodos auxiliares para extraer datos del token ---

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Desencripta el token usando la clave secreta
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Decodifica la clave secreta para usarla en el algoritmo de firma
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}