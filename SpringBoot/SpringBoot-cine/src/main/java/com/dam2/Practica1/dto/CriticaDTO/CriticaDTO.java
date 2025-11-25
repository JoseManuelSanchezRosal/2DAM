package com.dam2.Practica1.dto.CriticaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CriticaDTO {
    private Long id;
    private String comentario;
    private int nota;
    private String fecha;
}
