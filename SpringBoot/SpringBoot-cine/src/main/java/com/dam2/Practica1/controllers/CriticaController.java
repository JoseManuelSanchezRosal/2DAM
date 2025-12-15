package com.dam2.Practica1.controllers;

import com.dam2.Practica1.dto.CriticaDTO.CriticaCreateUpdateDTO;
import com.dam2.Practica1.dto.CriticaDTO.CriticaDTO;
import com.dam2.Practica1.models.Pelicula; // <--- Importante
import com.dam2.Practica1.repository.PeliculaRepository; // <--- Importante
import com.dam2.Practica1.service.CriticaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // ¡Importante para que React no falle!
@RestController
@RequestMapping("api/criticas")
@RequiredArgsConstructor
public class CriticaController {

    @Autowired
    private CriticaService service;

    @Autowired
    private PeliculaRepository peliculaRepository; // Inyectamos el repo de pelis

    @GetMapping
    public List<CriticaDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public CriticaDTO buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    // --- AQUÍ ESTABA EL FALLO ---
    // Hemos cambiado POST para que reciba el ID de la película en la URL
    @PostMapping("/{peliculaId}")
    public CriticaDTO agregar(@PathVariable Long peliculaId, @RequestBody @Valid CriticaCreateUpdateDTO criticaDto){

        // 1. Buscamos la película en la base de datos
        Pelicula p = peliculaRepository.findById(peliculaId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada con ID: " + peliculaId));

        // 2. Llamamos al servicio pasando los DOS argumentos que ahora pide
        return service.agregar(criticaDto, p);
    }

    @PutMapping("/{id}")
    public CriticaDTO actualizar(@PathVariable Long id, @RequestBody @Valid CriticaCreateUpdateDTO criticaDto){
        return service.actualizar(id, criticaDto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}