package com.dam2.Practica1.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "criticas")
@Data
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
}