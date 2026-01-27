package com.cine.entradas.service;

import com.cine.entradas.dto.PeliculaCreateDTO;
import com.cine.entradas.dto.PeliculaResponseDTO;
import com.cine.entradas.mapper.PeliculaMapper;
import com.cine.entradas.model.*;
import com.cine.entradas.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final PeliculaMapper peliculaMapper;

    @Transactional(readOnly = true)
    public List<PeliculaResponseDTO> findAll() {
        return peliculaRepository.findAll().stream()
                .map(peliculaMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public PeliculaResponseDTO create(PeliculaCreateDTO dto) {
        // 1. Convertir datos simples (titulo, duración...)
        Pelicula pelicula = peliculaMapper.toEntity(dto);

        // 2. Resolver relaciones usando los Repositorios (Lógica pura)
        Director director = directorRepository.findById(dto.getDirectorId())
                .orElseThrow(() -> new EntityNotFoundException("Director no encontrado"));

        List<Actor> actores = actorRepository.findAllById(dto.getActorIds());

        // Validación extra opcional
        if (actores.size() != dto.getActorIds().size()) {
            throw new EntityNotFoundException("Faltan actores por encontrar");
        }

        // 3. Establecer relaciones en la Entidad
        pelicula.setDirector(director);
        pelicula.setActores(actores);

        // 4. Guardar y devolver DTO de respuesta
        return peliculaMapper.toResponseDTO(peliculaRepository.save(pelicula));
    }
}