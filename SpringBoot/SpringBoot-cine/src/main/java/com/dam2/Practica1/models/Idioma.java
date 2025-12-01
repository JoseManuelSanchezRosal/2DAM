package com.dam2.Practica1.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
