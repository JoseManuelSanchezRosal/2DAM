package com.tuturno.dto.cita;

import java.time.LocalDateTime;

public record CitaResponseDTO(
        Long id,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        String nombreCliente,
        String nombreServicio,
        Double precio
) {}