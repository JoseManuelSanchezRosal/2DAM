package com.cine.entradas.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * CONFIGURACIÓN DE SWAGGER / OPENAPI
 * ----------------------------------
 * Configura la documentación automática de la API.
 * Lo más importante aquí es la configuración de seguridad 'bearerAuth'
 * para que aparezca el botón "Authorize" en la web.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "API Cine Entradas", version = "1.0.0"),
        security = @SecurityRequirement(name = "bearerAuth") // Aplica seguridad por defecto a los endpoints
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT" // Indica a Swagger que debe enviar la cabecera 'Authorization: Bearer <token>'
)
public class OpenApiConfig {
}