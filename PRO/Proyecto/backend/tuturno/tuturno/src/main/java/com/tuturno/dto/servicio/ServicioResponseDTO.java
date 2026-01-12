package com.tuturno.dto.servicio;

import java.math.BigDecimal;

public record ServicioResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer duracionMinutos,
        Boolean activo
) {}