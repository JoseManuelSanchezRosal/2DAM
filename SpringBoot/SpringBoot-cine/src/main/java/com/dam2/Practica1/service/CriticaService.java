package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.CriticaDTO.CriticaCreateUpdateDTO;
import com.dam2.Practica1.dto.CriticaDTO.CriticaDTO;
import com.dam2.Practica1.models.Critica;
import com.dam2.Practica1.models.Pelicula;
import com.dam2.Practica1.repository.CriticaRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
public class CriticaService {

    @Autowired
    CriticaRepository criticaRepository;

    private CriticaDTO toDTO(Critica c){
        return new CriticaDTO(
                c.getId(),
                c.getComentario(),
                c.getNota(),
                c.getFecha(),
                // Si el usuario es nulo, mostramos "Anónimo"
                c.getUsuario() != null ? c.getUsuario().getUserName() : "Anónimo"
        );
    }

    public List<CriticaDTO> listar() {
        return criticaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public CriticaDTO buscarPorId(Long id) {
        return criticaRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    // --- MÉTODO AGREGAR CORREGIDO ---
    public CriticaDTO agregar(CriticaCreateUpdateDTO criticaDto, Pelicula pelicula) {
        Critica c = new Critica();

        c.setComentario(criticaDto.getComentario());
        c.setNota(criticaDto.getNota());
        c.setFecha(criticaDto.getFecha());

        // VITAL: Enlazamos la crítica con la película
        c.setPelicula(pelicula);

        // BORRADO: c.setUsuario(); -> No lo necesitamos, se guardará como NULL automáticamente

        criticaRepository.save(c);

        return toDTO(c);
    }

    public CriticaDTO actualizar(Long id, CriticaCreateUpdateDTO criticaDto) {
        Optional<Critica> optionalCritica = criticaRepository.findById(id);
        if (!optionalCritica.isPresent()){
            throw new RuntimeException("Critica no encontrada");
        }
        Critica c = optionalCritica.get();

        c.setComentario(criticaDto.getComentario());
        c.setNota(criticaDto.getNota());
        c.setFecha(criticaDto.getFecha());

        criticaRepository.save(c);

        return toDTO(c);
    }

    public void eliminar(Long id) {
        criticaRepository.deleteById(id);
    }
}