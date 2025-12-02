package com.dam2.Practica1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="plataformas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 120)
    private String nombre;

    @Column(nullable = true, length = 255)
    private String url;

    //-----------------------------INTERRELACIONES---------------------------------------

    // N:M con PELICULAS (lado inverso
    @ManyToMany(mappedBy = "plataformas") // Busca polataformas en "clase pelicula"
    @JsonIgnore // Cortamos el bucle radicalmente, no muestra pelis al pedir plataforma
    private List<Pelicula> peliculas = new ArrayList<>();
}