package com.cine.entradas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class PeliculaCreateDTO {
    @NotBlank
    private String titulo;

    private int duracion;
    private int edadMinima;

    @NotNull
    private Long directorId; // Referencia por ID

    private List<Long> actorIds; // Referencia por IDs
}