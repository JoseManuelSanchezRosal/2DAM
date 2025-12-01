package com.dam2.Practica1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.tool.schema.spi.SchemaTruncator;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name="plataformas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 120)
    private String nombre;

    @Column(nullable = true, length = 255)
    private String url;
}
