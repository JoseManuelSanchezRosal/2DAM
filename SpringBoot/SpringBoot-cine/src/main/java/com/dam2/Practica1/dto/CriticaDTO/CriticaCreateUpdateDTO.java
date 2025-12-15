package com.dam2.Practica1.dto.CriticaDTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CriticaCreateUpdateDTO {

    @Size(max = 2500, message = "La critica no puede superar los 2500 caracteres")
    private String comentario;
    private int nota;
    private String fecha;
    private String nombreUsuario;
}
