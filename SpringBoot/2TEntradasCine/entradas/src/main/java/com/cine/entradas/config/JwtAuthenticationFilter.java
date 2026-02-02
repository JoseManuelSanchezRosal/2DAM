package com.cine.entradas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * FILTRO DE AUTENTICACIÓN JWT
 * ---------------------------
 * Se ejecuta UNA VEZ por cada petición (OncePerRequestFilter).
 * Verifica si la petición trae un token válido en la cabecera.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Obtener la cabecera "Authorization"
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Si no hay cabecera o no empieza por "Bearer ", no hacemos nada y dejamos pasar (pueden ser rutas públicas)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token limpio (sin la palabra "Bearer ")
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); // Extraer email del token

        // 4. Si hay email y el usuario NO está autenticado todavía en el contexto actual...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Buscamos al usuario en BD
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 5. Validamos si el token es correcto matemática y temporalmente
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Creamos un objeto de autenticación de Spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // Añadimos detalles de la petición (IP, sesión, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. ¡IMPORTANTE! "Logueamos" al usuario manualmente en el contexto de seguridad para esta petición
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 7. Continuar con el siguiente filtro o llegar al Controlador
        filterChain.doFilter(request, response);
    }
}