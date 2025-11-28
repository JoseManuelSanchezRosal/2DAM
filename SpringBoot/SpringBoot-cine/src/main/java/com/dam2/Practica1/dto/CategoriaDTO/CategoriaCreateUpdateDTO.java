package com.dam2.Practica1.dto.CategoriaDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CategoriaCreateUpdateDTO {

    @NotBlank(message = "El nombre de la categoria no puede estar en blanco")
    private String nombre;
}
