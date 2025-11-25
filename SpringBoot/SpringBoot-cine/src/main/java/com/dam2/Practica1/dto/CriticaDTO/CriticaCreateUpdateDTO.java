package com.dam2.Practica1.dto.CriticaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CriticaCreateUpdateDTO {
    private String comentario;
    private int nota;
    private String fecha;
}
