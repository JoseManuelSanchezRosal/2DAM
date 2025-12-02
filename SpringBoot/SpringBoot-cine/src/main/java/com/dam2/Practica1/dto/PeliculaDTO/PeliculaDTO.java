package com.dam2.Practica1.dto.PeliculaDTO;

import com.dam2.Practica1.models.Categoria;
import com.dam2.Practica1.models.Director;
import com.dam2.Practica1.models.Idioma;
import com.dam2.Practica1.models.Plataforma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.DirectColorModel;
import java.io.DataInputStream;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Donde vamos a filtrar los atributos que el usuario al acceder, le vamos a dejar ver (GET)
public class PeliculaDTO {
    private long id;
    private String titulo;
    private int duracion;
    private String fechaEstreno;
    private String sinopsis;
    private int valoracion;

    /*// Nuevos campos para mostrar relaciones (salida REAL)
    // Devolvemos el objeto DIRECTOR para que se vea su nombre
    private Director director;

    // Devolvemos las listas de OBJETOS completos
    private List<Categoria> categorias;
    private List<Plataforma> plataformas;
    private List<Idioma> idiomas;

    //private List<Actor> actores; OPCIONAL
*/
}