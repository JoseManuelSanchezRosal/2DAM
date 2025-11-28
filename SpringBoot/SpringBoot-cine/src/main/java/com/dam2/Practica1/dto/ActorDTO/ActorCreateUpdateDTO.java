package com.dam2.Practica1.dto.ActorDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ActorCreateUpdateDTO {

    @NotBlank(message = "El nombre del actor no puede ir en blanco")
    private String nombre;
}
