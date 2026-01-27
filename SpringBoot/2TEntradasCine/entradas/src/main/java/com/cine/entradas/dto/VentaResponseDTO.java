package com.cine.entradas.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private double importeTotal;
    private String estado;

    // Info plana del usuario para no hacer bucles
    private Long usuarioId;
    private String usuarioEmail;

    // Detalle de las entradas compradas
    private List<EntradaResponseDTO> entradas;
}