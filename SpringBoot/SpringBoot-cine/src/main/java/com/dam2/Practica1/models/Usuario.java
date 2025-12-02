package com.dam2.Practica1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String userName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @Column(nullable = false, length = 120)
    private String rol;

    //------------------------------------- INTERRELACIONES---------------------------------------

    // Relación 1:N con CRÍTICA
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore // Para no ver todas las críticas cuando carguemos el usuario
    private List<Critica> criticasRealizadas = new ArrayList<>();
}