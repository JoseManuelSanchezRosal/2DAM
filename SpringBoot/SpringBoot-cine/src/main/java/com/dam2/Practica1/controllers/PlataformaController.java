package com.dam2.Practica1.controllers;

import com.dam2.Practica1.dto.PlataformaDTO.PlataformaCreateUpdateDTO;
import com.dam2.Practica1.dto.PlataformaDTO.PlataformaDTO;
import com.dam2.Practica1.service.PlataformaService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plataformas")
@RequiredArgsConstructor

public class PlataformaController {

    @Autowired
    PlataformaService service;

    @GetMapping
    private List<PlataformaDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    private PlataformaDTO buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    private PlataformaDTO agregar(@RequestBody PlataformaCreateUpdateDTO plataformaDto){
        return service.agregar(plataformaDto);
    }

    @PutMapping("/{id}")
    private PlataformaDTO actualizar(@PathVariable Long id, PlataformaCreateUpdateDTO plataformaDto){
        return service.actualizar(id, plataformaDto);
    }

    @DeleteMapping("/{id}")
    private void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
