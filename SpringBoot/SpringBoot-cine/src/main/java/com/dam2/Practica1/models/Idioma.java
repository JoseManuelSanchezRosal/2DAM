package com.dam2.Practica1.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "idiomas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 120, unique = true)
    private String nombre;

    //-----------------INTERRELACIONES---------------------

    // IDIOMA--N:M--PELICULAS
    @ManyToMany(mappedBy = "idiomas")
    @JsonIgnore // Cortamos el bucle aqui
    private List<Pelicula> peliculas = new ArrayList<>();
}
