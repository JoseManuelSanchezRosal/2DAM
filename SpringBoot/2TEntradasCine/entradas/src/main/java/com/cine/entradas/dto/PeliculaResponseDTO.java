package com.cine.entradas.dto;

import lombok.Data;
import java.util.List;

@Data
public class PeliculaResponseDTO {
    private Long id;
    private String titulo;
    private int duracion;
    private int edadMinima;

    private DirectorDTO director;    // Objeto completo
    private List<ActorDTO> actores;  // Lista de objetos
}