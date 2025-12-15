package com.dam2.Practica1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "criticas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Critica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false)
    private int nota; // ¿Esta nota no tiene nada que ver con la valoracion cierto?

    @Column(nullable = false)
    private String fecha; //     Cambiada a localdate para poder ordenarlas en SQL (01/03/2022)

    // -----------------------------INTERRELACIONES------------------------------------

    /**
     * RELACIÓN N:1 con USUARIO
     * - nullable = false: Una crítica NO puede existir sin un usuario (Integridad obligatoria).
     * - @JsonIgnoreProperties: Al serializar la crítica, vemos el usuario, pero NO sus otras críticas.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)// Cambiado a true para poder tener criticas con usuario null
    @JsonIgnoreProperties("criticasRealizadas") // Evita bucle con USUARIO
    private Usuario usuario;


    /**
     * RELACIÓN N:1 con PELÍCULA
     * - nullable = false: Una crítica debe pertenecer a una película.
     * - @JsonIgnoreProperties: Vemos la película (título, etc.), pero NO su lista de críticas (evita bucle).
     */
    @ManyToOne
    @JoinColumn(name = "pelicula_id", nullable = false)
    @JsonIgnoreProperties("listaCriticas") // Evita  bucle con PELICULA
    private Pelicula pelicula;
}