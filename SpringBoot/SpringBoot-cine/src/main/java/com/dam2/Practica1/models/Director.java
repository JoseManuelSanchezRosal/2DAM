package com.dam2.Practica1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "directores")
@Data  // ✅ Lombok genera getters, setters, toString, equals, hashCode
@AllArgsConstructor      // ✅ genera constructor con todos los campos
@NoArgsConstructor

public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true)
    private String nombre;

    //-------------------INTERRELACIONES----------------

    // Relación 1:N con Pelicula
    @OneToMany(mappedBy = "director")
    @JsonIgnore
    private List<Pelicula> peliculas = new ArrayList<>();
}
