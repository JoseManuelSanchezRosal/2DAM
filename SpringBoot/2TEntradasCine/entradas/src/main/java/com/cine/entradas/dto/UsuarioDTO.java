package com.cine.entradas.dto;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    // IMPORTANTE: Nunca devolvemos la contraseña
}