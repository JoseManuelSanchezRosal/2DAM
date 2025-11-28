package com.dam2.Practica1.controllers;


import com.dam2.Practica1.dto.PeliculaDTO.ImportarPeliculaDto;
import com.dam2.Practica1.dto.PeliculaDTO.PeliculaCreateUpdateDTO;
import com.dam2.Practica1.dto.PeliculaDTO.PeliculaDTO;
import com.dam2.Practica1.models.Pelicula;
import com.dam2.Practica1.service.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/peliculas")
@RequiredArgsConstructor
public class PeliculaController {

    @Autowired
    private PeliculaService service;

    //---------------FUNCIONALIDAD CRUD CLASE 3 PeliculaDTO-----------------------
    // GET /peliculas
    @GetMapping
    public List<PeliculaDTO> listar() {
        return service.listar();
    }

    // GET /peliculas/{id}
    @GetMapping("/{id}")
    public PeliculaDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // POST
    // COMENTAR ------------------------
    @PostMapping
    public PeliculaDTO agregar(@RequestBody @Valid PeliculaCreateUpdateDTO peliculaDto) {
        return service.agregar(peliculaDto);
    }

    // PUT
    // COMENTAR -----------------------
    @PutMapping("/{id}")
    public PeliculaDTO actualizar(@PathVariable Long id, @RequestBody @Valid PeliculaCreateUpdateDTO peliculaDto){
        return service.actualizar(id, peliculaDto);
    }

    // DELETE
    // COMENTAR-----------------------
    @DeleteMapping("{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }


    // Ejercicio 1.2 y 1.3
    @GetMapping("/procesar")
    public String procesarPeliculas() {
        return this.service.procesarPeliculas();
    }

    // Ejercicio 1.5
    @GetMapping("/procesar-async")
    public CompletableFuture<String> procesarAsync() {
        return this.service.procesarPeliculasAsync();
    }

    // A4 - Ejercicio 2
    @GetMapping("/reproducir")
    public String reproducirAsync() {
        return this.service.reproducirAsync();
    }

    // A4 - Ejercicio 3
    @PostMapping("/importar-peliculas")
    public CompletableFuture<String> importarPeliculas(@RequestBody ImportarPeliculaDto importarPeliculaDto) throws IOException {
            return this.service.importarPeliculas(importarPeliculaDto.path());
    }

    // A4 - Ejercicio 4
    /**
     * Endpoint REST para iniciar la votacion del Jurado
     * @param numeroJurados es la cantidad de Jurados (hilos) van a realizar la votacion
     * @return un Map con Nombre y Valoracion de cada pelicula
     * @throws InterruptedException
     */
    @GetMapping("/oscar/{numeroJurados}")
    public Map<String, Integer> votacion(@PathVariable int numeroJurados) throws InterruptedException {
        return service.realizarVotaciones(numeroJurados);
    }
}