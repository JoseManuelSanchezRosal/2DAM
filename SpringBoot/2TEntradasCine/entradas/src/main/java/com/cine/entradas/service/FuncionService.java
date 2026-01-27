package com.cine.entradas.service;

import com.cine.entradas.dto.FuncionCreateDTO;
import com.cine.entradas.dto.FuncionResponseDTO;
import com.cine.entradas.mapper.FuncionMapper;
import com.cine.entradas.model.Funcion;
import com.cine.entradas.model.Pelicula;
import com.cine.entradas.model.Sala;
import com.cine.entradas.repository.FuncionRepository;
import com.cine.entradas.repository.PeliculaRepository;
import com.cine.entradas.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionService {

    private final FuncionRepository funcionRepo;
    private final PeliculaRepository peliculaRepo;
    private final SalaRepository salaRepo;
    private final FuncionMapper funcionMapper;

    @Transactional(readOnly = true)
    public List<FuncionResponseDTO> findAll() {
        return funcionRepo.findAll().stream()
                .map(funcionMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public FuncionResponseDTO create(FuncionCreateDTO dto) {
        // 1. Convertir datos básicos (fecha, precio)
        Funcion funcion = funcionMapper.toEntity(dto);

        // 2. Buscar Entidades por ID (Lógica de Negocio)
        Pelicula pelicula = peliculaRepo.findById(dto.getPeliculaId())
                .orElseThrow(() -> new EntityNotFoundException("Película no encontrada"));

        Sala sala = salaRepo.findById(dto.getSalaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala no encontrada"));

        // 3. Asignar relaciones
        funcion.setPelicula(pelicula);
        funcion.setSala(sala);

        // 4. Guardar
        return funcionMapper.toResponseDTO(funcionRepo.save(funcion));
    }
}