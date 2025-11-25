package com.dam2.Practica1.repository;

import com.dam2.Practica1.models.Pelicula;
import com.dam2.Practica1.models.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}