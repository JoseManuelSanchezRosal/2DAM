package com.dam2.Practica1.controllers;

import com.dam2.Practica1.dto.DirectorDTO.DirectorCreateUpdateDTO;
import com.dam2.Practica1.dto.DirectorDTO.DirectorDTO;
import com.dam2.Practica1.service.DirectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directores")
@RequiredArgsConstructor
public class DirectorController {
    @Autowired
    private DirectorService service;

    @GetMapping
    private List<DirectorDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    private DirectorDTO buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    private DirectorDTO agregar(@RequestBody @Valid DirectorCreateUpdateDTO directorDto){
        return service.agregar(directorDto);
    }

    @PutMapping("/{id}")
    private DirectorDTO actualizar(@PathVariable Long id, @RequestBody @Valid DirectorCreateUpdateDTO directorDto){
        return service.actualizar(id, directorDto);
    }

    @DeleteMapping("/{id}")
    private void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
