package com.dam2.Practica1.dto.PeliculaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Donde vamos a filtrar los atributos que el usuario al acceder, le vamos a dejar ver
public class PeliculaDTO {
    private long id;
    private String titulo;
    private int duracion;
    private LocalDate fechaEstreno;
    private String sinopsis;
    private int valoracion;
}
