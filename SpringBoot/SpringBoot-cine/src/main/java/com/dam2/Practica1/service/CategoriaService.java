package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.CategoriaDTO.CategoriaCreateUpdateDTO;
import com.dam2.Practica1.dto.CategoriaDTO.CategoriaDTO;
import com.dam2.Practica1.models.Categoria;
import com.dam2.Practica1.repository.CategoriaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Getter

public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    private CategoriaDTO toDTO(Categoria c){
        return new CategoriaDTO(
                c.getId(),
                c.getNombre()
        );
    }

    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CategoriaDTO buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public CategoriaDTO agregar(CategoriaCreateUpdateDTO categoriaDto) {
        Categoria c = new Categoria();
        c.setNombre(categoriaDto.getNombre());
        categoriaRepository.save(c);
        return toDTO(c);
    }

    public CategoriaDTO actualizar(Long id, CategoriaCreateUpdateDTO categoriaDto) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if (!optionalCategoria.isPresent()){
            throw new RuntimeException("Categoria no encontrada");
        }
        Categoria c = optionalCategoria.get();
        c.setNombre(categoriaDto.getNombre());
        categoriaRepository.save(c);

        return toDTO(c);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}