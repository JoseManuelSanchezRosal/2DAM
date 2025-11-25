package com.dam2.Practica1.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "idiomas")
@Data
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 120, unique = true)
    private String nombre;

}
