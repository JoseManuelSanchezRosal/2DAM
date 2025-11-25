package com.dam2.Practica1.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fichas_tecnicas")
@Data  // ✅ Lombok genera getters, setters, toString, equals, hashCode
@AllArgsConstructor      // ✅ genera constructor con todos los campos
@NoArgsConstructor
public class FichaTecnica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String director;
    private int duracion;
    private String pais;
}
