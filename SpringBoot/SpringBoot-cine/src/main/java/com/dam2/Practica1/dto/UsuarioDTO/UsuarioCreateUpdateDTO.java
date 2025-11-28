package com.dam2.Practica1.dto.UsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioCreateUpdateDTO {
    private String userName;
    private String email;
    private String password; // Se incluye para poder ser creada/modificada por el ususario
    private String rol;
}
