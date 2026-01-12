package com.tuturno.dto.servicio;

import java.math.BigDecimal;

public record ServicioRequestDTO(
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer duracionMinutos // En el JSON vendrá como "duracionMinutos"
) {}