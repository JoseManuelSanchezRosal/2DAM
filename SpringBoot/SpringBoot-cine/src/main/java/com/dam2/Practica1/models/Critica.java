package com.dam2.Practica1.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "criticas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Critica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comentario;

    @Column(nullable = false)
    private int nota; // ¿Esta nota no tiene nada que ver con la valoracion cierto?

    @Column(nullable = false)
    private String fecha;

    @ManyToOne
    private Usuario usuario;

}