package com.cine.entradas.config;

import com.cine.entradas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * CLASE DE CONFIGURACIÓN DE APLICACIÓN
 * ------------------------------------
 * Esta clase crea y expone los componentes (Beans) que Spring Security necesita
 * para saber CÓMO buscar usuarios y CÓMO verificar contraseñas.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository usuarioRepository;

    /**
     * 1. USER DETAILS SERVICE
     * Este bean define la lógica de búsqueda de usuarios en TU base de datos.
     * Es el puente entre Spring Security y tu repositorio JPA.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Expresión Lambda que implementa loadUserByUsername
        return username -> usuarioRepository.findByEmail(username)
                // Transformamos tu entidad 'Usuario' (JPA) al objeto 'UserDetails' que entiende Spring Security
                .map(usuario -> org.springframework.security.core.userdetails.User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getPassword()) // La contraseña ya debe estar encriptada aquí
                        .roles("USER") // Asignamos un rol por defecto para cumplir con el contrato
                        .build())
                // Si no existe, lanzamos la excepción estándar de seguridad
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));
    }

    /**
     * 2. AUTHENTICATION PROVIDER
     * Es el encargado de la lógica de autenticación (Data Access Object).
     * Dice: "Voy a usar este UserDetailsService para buscar al usuario y este PasswordEncoder para comparar claves".
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Le inyectamos el buscador de usuarios
        authProvider.setPasswordEncoder(passwordEncoder()); // Le inyectamos el encriptador
        return authProvider;
    }

    /**
     * 3. AUTHENTICATION MANAGER
     * Es el orquestador principal. El AuthController llamará a este objeto para intentar el login.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 4. PASSWORD ENCODER
     * Define el algoritmo de encriptación. BCrypt es el estándar actual seguro.
     * Nunca guardes contraseñas en texto plano.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}