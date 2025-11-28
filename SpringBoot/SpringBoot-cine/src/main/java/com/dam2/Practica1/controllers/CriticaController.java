package com.dam2.Practica1.controllers;

import com.dam2.Practica1.dto.CriticaDTO.CriticaCreateUpdateDTO;
import com.dam2.Practica1.dto.CriticaDTO.CriticaDTO;
import com.dam2.Practica1.service.CriticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/criticas")
@RequiredArgsConstructor

public class CriticaController {

    @Autowired
    private CriticaService service;

    @GetMapping
    private List<CriticaDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    private CriticaDTO buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping("/{id}")
    private CriticaDTO agregar(@RequestBody CriticaCreateUpdateDTO criticaDto){
        return service.agregar(criticaDto);
    }

    @PutMapping("/{id}")
    private CriticaDTO actualizar(@PathVariable Long id, @RequestBody CriticaCreateUpdateDTO criticaDto){
        return service.actualizar(id, criticaDto);
    }

    @DeleteMapping("/{id}")
    private void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }

}
