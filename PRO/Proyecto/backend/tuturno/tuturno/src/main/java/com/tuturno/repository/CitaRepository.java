package com.tuturno.repository;

import com.tuturno.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByUsuarioEmail(String email);

    @Query("SELECT c FROM Cita c LEFT JOIN FETCH c.servicios WHERE c.fecha = :fecha")
    List<Cita> findByFecha(@Param("fecha") LocalDate fecha);
}