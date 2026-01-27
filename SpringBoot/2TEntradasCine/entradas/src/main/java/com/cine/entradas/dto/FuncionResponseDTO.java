package com.cine.entradas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FuncionResponseDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private double precio;

    // Reutilizamos DTOs existentes (Clean Architecture)
    private PeliculaResponseDTO pelicula;
    private SalaDTO sala;
}