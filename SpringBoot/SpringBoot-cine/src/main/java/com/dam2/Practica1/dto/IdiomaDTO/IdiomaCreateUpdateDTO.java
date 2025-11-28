package com.dam2.Practica1.dto.IdiomaDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class IdiomaCreateUpdateDTO {

    @NotBlank(message = "El nombre del idioma debe ir especificado")
    private String nombre;
}
