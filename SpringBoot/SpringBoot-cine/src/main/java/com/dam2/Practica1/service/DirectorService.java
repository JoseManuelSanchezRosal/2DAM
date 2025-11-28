package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.DirectorDTO.DirectorCreateUpdateDTO;
import com.dam2.Practica1.dto.DirectorDTO.DirectorDTO;
import com.dam2.Practica1.models.Director;
import com.dam2.Practica1.repository.DirectorRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter

public class DirectorService {
    @Autowired
    DirectorRepository directorRepository;

    private DirectorDTO toDTO(Director d){
        return new DirectorDTO(
                d.getId(),
                d.getNombre()
        );
    }

    public List<DirectorDTO> listar() {
        return directorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public DirectorDTO buscarPorId(Long id) {
        return directorRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public DirectorDTO agregar(DirectorCreateUpdateDTO directorDto) {
        Director d = new Director();
        d.setNombre(d.getNombre());
        return toDTO(d);
    }

    public DirectorDTO actualizar(Long id, DirectorCreateUpdateDTO directorDto) {
        Optional<Director> optionalDirector = directorRepository.findById(id);
        if (!optionalDirector.isPresent()){
            throw new RuntimeException("Director no encontrado");
        }
        Director d = optionalDirector.get();
        d.setNombre(directorDto.getNombre());

        return toDTO(d);
    }

    public void eliminar(Long id) {
        directorRepository.deleteById(id);
    }
}














