package com.dam2.Practica1.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.tool.schema.spi.SchemaTruncator;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name="plataformas")
@Data
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 120)
    private String nombre;

    @Column(nullable = true, length = 255)
    private String url;


}
