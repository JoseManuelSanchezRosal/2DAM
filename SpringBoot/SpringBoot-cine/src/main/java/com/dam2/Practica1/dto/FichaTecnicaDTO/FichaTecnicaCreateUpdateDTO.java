package com.dam2.Practica1.dto.FichaTecnicaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FichaTecnicaCreateUpdateDTO {
    private String director;
    private int duracion;
    private String pais;
}
