package com.dam2.Practica1.controllers;

import com.dam2.Practica1.dto.IdiomaDTO.IdiomaCreateUpdateDTO;
import com.dam2.Practica1.dto.IdiomaDTO.IdiomaDTO;
import com.dam2.Practica1.service.IdiomaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/idiomas")
@RequiredArgsConstructor

public class IdiomaController {

    @Autowired
    private IdiomaService service;

    @GetMapping
    private List<IdiomaDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    private IdiomaDTO buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    private IdiomaDTO agregar(IdiomaCreateUpdateDTO idiomaDto){
        return service.agregar(idiomaDto);
    }

    @PutMapping("/{id}")
    private IdiomaDTO actualizar(@PathVariable Long id, IdiomaCreateUpdateDTO idiomaDto){
       return service.actualizar(id, idiomaDto);
    }

    @DeleteMapping("{id}")
    private void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
