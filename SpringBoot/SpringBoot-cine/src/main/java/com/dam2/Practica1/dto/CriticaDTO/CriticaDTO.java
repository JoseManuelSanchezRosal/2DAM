package com.dam2.Practica1.dto.CriticaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CriticaDTO {
    private Long id;
    private String comentario;
    private int nota;
    private String fecha;
}
