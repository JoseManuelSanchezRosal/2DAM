package com.cine.entradas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class VentaCreateDTO {
    @NotNull
    private Long usuarioId;

    @NotEmpty
    private List<DetalleEntradaDTO> entradas; // Lista de entradas a comprar
}