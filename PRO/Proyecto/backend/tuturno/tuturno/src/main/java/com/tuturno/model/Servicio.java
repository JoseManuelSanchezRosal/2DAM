package com.tuturno.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // ESTOS CAMPOS FALTABAN:
    @Column(nullable = false)
    private Integer duracion; // Lombok generará getDuracion() y setDuracion()

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private Boolean activo = true; // Lombok generará isActivo() o getActivo() y setActivo()
}