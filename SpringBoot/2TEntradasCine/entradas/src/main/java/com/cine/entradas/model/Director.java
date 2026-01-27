package com.cine.entradas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "director")
    private List<Pelicula> peliculas;

    // Constructor helper para el Seeder
    public Director(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}