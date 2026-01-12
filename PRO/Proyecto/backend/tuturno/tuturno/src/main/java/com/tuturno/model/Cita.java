package com.tuturno.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora_inicio", nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_fin", nullable = false)
    private LocalDateTime fechaHoraFin;

    // Relación Muchos a Uno: Muchas citas pueden ser de un Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación Muchos a Uno: Muchas citas pueden tener el mismo Servicio
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;
}