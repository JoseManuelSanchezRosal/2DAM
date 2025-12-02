package com.dam2.Practica1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categorias")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 120, unique = true)
    private String nombre;

    //-----------------------INTERRELACIONES---------------------
    //CATEGORIA--N:M--PELICULA
    @ManyToMany(mappedBy = "categorias")
    @JsonIgnore
    private List<Pelicula> peliculas = new ArrayList<>(); // Es importante inicializar
}
