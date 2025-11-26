package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.ActorDTO.ActorCreateUpdateDTO;
import com.dam2.Practica1.dto.ActorDTO.ActorDTO;
import com.dam2.Practica1.models.Actor;
import com.dam2.Practica1.repository.ActorRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;

@Service
@Getter

public class ActorService {

    @Autowired
    ActorRepository actorRepository;

    private ActorDTO toDTO(Actor a){
        return new ActorDTO(
                a.getId(),
                a.getNombre()
        );
    }

    public List<ActorDTO> listar() {
        return actorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ActorDTO buscarPorId(Long id) {
        return actorRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public ActorDTO agregar(ActorCreateUpdateDTO actorDto) {
        Actor a = new Actor();
        a.setNombre(actorDto.getNombre());
        actorRepository.save(a);
        return toDTO(a);
    }

    public ActorDTO actualizar(Long id, ActorCreateUpdateDTO actorDto) {
        Optional<Actor> optionalActor = actorRepository.findById(id);
        if (!optionalActor.isPresent()){
            throw new RuntimeException("Actor no encontrado");
        }
        Actor a = optionalActor.get();
        a.setNombre(actorDto.getNombre());
        actorRepository.save(a);

        return toDTO(a);
    }

    public void eliminar(Long id) {
        actorRepository.deleteById(id);
    }
}