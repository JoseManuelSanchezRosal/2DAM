package com.cine.entradas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future; // Opcional, para validar fechas
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FuncionCreateDTO {
    @NotNull
    private LocalDateTime fechaHora; // Formato ISO: "2026-02-20T18:30:00"

    private double precio;

    @NotNull
    private Long peliculaId; // Referencia por ID

    @NotNull
    private Long salaId;     // Referencia por ID
}