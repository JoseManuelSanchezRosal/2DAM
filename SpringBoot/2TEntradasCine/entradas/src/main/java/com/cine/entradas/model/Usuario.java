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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String password; // ¡Asegúrate de tener este campo!

    @OneToMany(mappedBy = "usuario")
    private List<Venta> ventas;

    // --- AÑADE ESTE CONSTRUCTOR ---
    // Este es el que busca tu CineDataLoader: (ID, Nombre, Email, Password)
    public Usuario(Long id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
}