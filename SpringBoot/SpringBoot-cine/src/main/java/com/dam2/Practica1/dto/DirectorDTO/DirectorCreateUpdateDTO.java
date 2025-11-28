package com.dam2.Practica1.dto.DirectorDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DirectorCreateUpdateDTO {

    @NotBlank(message = "El nombre del Director debe quedar especificado")
    private String nombre;
}
