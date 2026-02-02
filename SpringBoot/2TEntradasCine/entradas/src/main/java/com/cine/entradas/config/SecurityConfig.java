package com.cine.entradas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * CONFIGURACIÓN DE SEGURIDAD
 * --------------------------
 * Aquí se definen las reglas de acceso HTTP (quién puede ver qué).
 * Se habilita la seguridad web.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter; // Nuestro filtro personalizado
    private final AuthenticationProvider authenticationProvider; // El proveedor configurado en ApplicationConfig

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF (Cross-Site Request Forgery) porque al usar Tokens no hay sesión de navegador vulnerable
                .csrf(csrf -> csrf.disable())

                // DEFINICIÓN DE RUTAS (Whitelisting)
                .authorizeHttpRequests(auth -> auth
                        // 1. Documentación (Swagger y OpenAPI) -> PÚBLICO
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 2. Auth (Login) y Catálogos (Películas/Funciones) -> PÚBLICO
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/peliculas", "/api/peliculas/**").permitAll()
                        .requestMatchers("/api/funciones", "/api/funciones/**").permitAll()

                        // 3. CUALQUIER OTRA COSA (Ventas, Usuarios) -> REQUIERE AUTENTICACIÓN
                        .anyRequest().authenticated()
                )

                // Gestión de sesión STATELESS (Sin estado).
                // No guardamos sesión en el servidor. Cada petición debe traer su Token.
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Añadimos nuestro proveedor de autenticación
                .authenticationProvider(authenticationProvider)

                // Añadimos nuestro filtro JWT ANTES del filtro estándar de usuario/password
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}