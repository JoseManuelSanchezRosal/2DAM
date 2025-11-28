package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.CategoriaDTO.CategoriaDTO;
import com.dam2.Practica1.dto.IdiomaDTO.IdiomaCreateUpdateDTO;
import com.dam2.Practica1.dto.IdiomaDTO.IdiomaDTO;
import com.dam2.Practica1.models.Idioma;
import com.dam2.Practica1.repository.IdiomaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Service
@Getter

public class IdiomaService {

    @Autowired
    IdiomaRepository idiomaRepository;

    private IdiomaDTO toDTO(Idioma i){
        return new IdiomaDTO(
                i.getId(),
                i.getNombre()
        );
    }

    public List<IdiomaDTO> listar() {
        return idiomaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public IdiomaDTO buscarPorId(Long id) {
        return idiomaRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public IdiomaDTO agregar(IdiomaCreateUpdateDTO idiomaDto) {
        Idioma i = new Idioma();
        i.setNombre(idiomaDto.getNombre());
        return toDTO(i);
    }

    public IdiomaDTO actualizar(Long id, IdiomaCreateUpdateDTO idiomaDto) {
        Optional<Idioma> optionalIdioma = idiomaRepository.findById(id);
        if (!optionalIdioma.isPresent()){
            throw new RuntimeException("Idioma no encontrado");
        }
        Idioma i = optionalIdioma.get();
        i.setNombre(idiomaDto.getNombre());
        idiomaRepository.save(i);
        return toDTO(i);
    }

    public void eliminar(Long id) {
        idiomaRepository.deleteById(id);
    }
}
