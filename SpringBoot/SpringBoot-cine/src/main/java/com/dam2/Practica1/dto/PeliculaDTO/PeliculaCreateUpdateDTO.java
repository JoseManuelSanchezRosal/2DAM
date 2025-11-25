package com.dam2.Practica1.dto.PeliculaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PeliculaCreateUpdateDTO {
    private String titulo;
    private int duracion;
    private LocalDate fechaEstreno;
    private String sinopsis;
    private int valoracion;
}
