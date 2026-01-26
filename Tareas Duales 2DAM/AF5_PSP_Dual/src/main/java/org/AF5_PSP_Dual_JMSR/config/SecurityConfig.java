package org.AF5_PSP_Dual_JMSR.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * CONFIGURACIÓN DE SEGURIDAD - CUMPLE CON EL RA5
 * Definimos usuarios, roles y permisos sobre las rutas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // RA5.j y RA5.k: Definimos usuarios y roles (Esquemas basados en roles). [cite: 123, 124]
    // RA5.h: Aplicamos principios de seguridad (Autenticación). [cite: 121]
    @Bean
    public UserDetailsService users() {
        // Creamos un usuario "visitante" que solo podrá leer
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}1234") // {noop} indica que no encriptamos la clave para este ejemplo escolar
                .roles("USER")
                .build();

        // Creamos un usuario "admin" que podrá modificar la BBDD
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();

        // Los guardamos en memoria para gestionar la autenticación
        return new InMemoryUserDetailsManager(user, admin);
    }

    // RA5.j: Definimos políticas para limitar el acceso. [cite: 123]
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF para facilitar las pruebas con Postman en este ejercicio
                .csrf(csrf -> csrf.disable())

                // Configuramos las reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // RA5: Restringimos acceso. Solo ADMIN puede escribir (POST, PUT, DELETE) [cite: 91]
                        .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")

                        // Permitimos que USER y ADMIN puedan leer (GET)
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").authenticated()

                        // Cualquier otra cosa requiere autenticación
                        .anyRequest().authenticated()
                )
                // RA4.b y RA5.n: Usamos HTTP Basic (Estándar sencillo de autenticación). [cite: 113, 127]
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}