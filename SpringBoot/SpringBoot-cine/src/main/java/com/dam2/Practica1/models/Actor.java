package com.dam2.Practica1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actores")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    /**
     * RELACIÓN N:M (Lado Propietario)
     * - Actor define la tabla intermedia 'actor_pelicula'.
     */
    @ManyToMany
    @JoinTable(
            name="actor_pelicula",
            joinColumns = @JoinColumn(name="actor_id"),
            inverseJoinColumns = @JoinColumn(name="pelicula_id"))
    @JsonIgnoreProperties("actores")
    private List<Pelicula> peliculas = new ArrayList<>();

    // Metodo Helper ADD PELICULA
    public void addPelicula(Pelicula p){
        if(p == null || this.peliculas.contains(p)){
            return;
        }
        // 1 Añado una pelicula a mi lista (yo como Actor)
        this.peliculas.add(p);

        // 2 Cojo la pelicula, busco su lista de actores y me añado a mi mismo (yo como Actor)
        p.getActores().add(this);
    }

    // Metodo Helper REMOVE PELICULA
    public void removePelicula(Pelicula p){
        if(p == null){
            return;
        }
        this.peliculas.remove(p);
        p.getActores().remove(this);
    }
}