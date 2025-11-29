package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.PlataformaDTO.PlataformaCreateUpdateDTO;
import com.dam2.Practica1.dto.PlataformaDTO.PlataformaDTO;
import com.dam2.Practica1.models.Plataforma;
import com.dam2.Practica1.repository.PlataformaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Getter

public class PlataformaService {

    @Autowired
    PlataformaRepository plataformaRepository;

    private PlataformaDTO toDTO(Plataforma p) {
        return new PlataformaDTO(
                p.getId(),
                p.getNombre(),
                p.getUrl()
        );
    }

    public List<PlataformaDTO> listar() {
        return plataformaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PlataformaDTO buscarPorId(Long id) {
        return plataformaRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public PlataformaDTO agregar(PlataformaCreateUpdateDTO plataformaDto) {
        Plataforma p = new Plataforma();
        p.setNombre(plataformaDto.getNombre());
        p.setUrl(plataformaDto.getUrl());

        plataformaRepository.save(p);

        return toDTO(p);
    }

    public PlataformaDTO actualizar(Long id, PlataformaCreateUpdateDTO plataformaDto) {
        Optional<Plataforma> optionalPlataforma = plataformaRepository.findById(id);
        if (!optionalPlataforma.isPresent()) {
            throw new RuntimeException("Plataforma no encontrada");
        }
        Plataforma p = optionalPlataforma.get();
        p.setNombre(plataformaDto.getNombre());
        p.setUrl(plataformaDto.getUrl());

        plataformaRepository.save(p);

        return toDTO(p);
    }

    public void eliminar(Long id) {
        plataformaRepository.deleteById(id);
    }
}
