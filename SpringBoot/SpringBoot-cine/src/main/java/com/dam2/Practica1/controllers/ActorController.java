package com.dam2.Practica1.controllers;

import com.dam2.Practica1.dto.ActorDTO.ActorCreateUpdateDTO;
import com.dam2.Practica1.dto.ActorDTO.ActorDTO;
import com.dam2.Practica1.service.ActorService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/actores")
@RequiredArgsConstructor

public class ActorController {
    private ActorService service;

    //----------CRUD ACTOR-----------
    @GetMapping
    public List<ActorDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ActorDTO buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public ActorDTO agregar(@RequestBody @Valid ActorCreateUpdateDTO actorDto){
        return service.agregar(actorDto);
    }

    @PutMapping("{id}")
    public ActorDTO actualizar(@PathVariable Long id, @RequestBody @Valid ActorCreateUpdateDTO actorDto){
        return service.actualizar(id, actorDto);
    }

    @DeleteMapping("{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}