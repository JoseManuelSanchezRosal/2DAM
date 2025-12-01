package com.dam2.Practica1.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actores")
@Data  // ✅ Lombok genera getters, setters, toString, equals, hashCode
@AllArgsConstructor      // ✅ genera constructor con todos los campos
@NoArgsConstructor

public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;


    /**
     * ManyToMany crea una tabla intermedia
     * name ================> define el nombre de la tabla: actor_pelicula
     * joinColumns =========> la columna FK que apunta a esta entidad (actor_id)
     * InverseJoinColumns===> la columna FK que apunta al otro lado de la relacion (pelicula_id)
     */
    @ManyToMany
    @JoinTable(
            name="actor_pelicula",
            joinColumns = @JoinColumn(name="actor_id"),
            inverseJoinColumns = @JoinColumn(name="pelicula_id"))
    private List<Pelicula> peliculas = new ArrayList<>();

    /**
     * Metodo Helper que se encarga de escribir en las dos listas al mismo tiempo ((List<Actor> en Pelicula) y (List<Pelicula> en Actor))
     * @param p Pelicula que desde Actor añadimos a nuestra lista y a su vez, el actor a la lista de actores de la clase pelicula
     */
    public void addPelicula(Pelicula p){
        // 1 Añado una pelicula a mi lista (yo como Actor)
        peliculas.add(p);

        // 2 Cojo la pelicula, busco su lista de actores y me añado a mi mismo (yo como Actor)
        p.getActors().add(this);
    }
}