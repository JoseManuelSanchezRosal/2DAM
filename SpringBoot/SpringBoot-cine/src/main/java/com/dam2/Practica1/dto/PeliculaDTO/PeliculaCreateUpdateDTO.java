package com.dam2.Practica1.dto.PeliculaDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PeliculaCreateUpdateDTO {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @Min(value = 1, message = "La duracion debe ser mayor que 0")
    private int duracion;

    @NotNull(message = "La fecha de estreno debe ser obligatoria")
    private String fechaEstreno;

    @Size(max = 500, message = "La sinopsis no puede ser mayor a 500 caracteres")
    private String sinopsis;

    @Min(value = 0, message = "La valoracion minima es 0")
    @Max(value = 10, message = "La valoracion maxima es de 10")
    private int valoracion;

    // Nuevos Campos (no suelen ser obligatorios, porque podemos crear la ficha sin foto)
    private String posterPath;
    private String backdropPath;

    /*// NUEVOS CAMPOS PARA LAS RELACIONES N:M (ENTRADA DE ID'S)
    // Listas de ID's
    private List<Long> categoriaIds;
    private List<Long> plataformaIds;
    private List<Long> idiomaIds;

    // private List<Long> actorIds; (si decidimos implementarlo en el servicio)*/
}
