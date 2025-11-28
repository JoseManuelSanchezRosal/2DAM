package com.dam2.Practica1.controllers;

import com.dam2.Practica1.dto.CategoriaDTO.CategoriaCreateUpdateDTO;
import com.dam2.Practica1.dto.CategoriaDTO.CategoriaDTO;
import com.dam2.Practica1.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor

public class CategoriaController {

    @Autowired
    private CategoriaService service;

    //----------CRUD ACTOR-----------
    @GetMapping
    public List<CategoriaDTO> listar(){
        return service.listar();

    }

    @GetMapping("/{id}")
    public CategoriaDTO buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public CategoriaDTO agregar(@RequestBody CategoriaCreateUpdateDTO categoriaDto){
        return service.agregar(categoriaDto);
    }

    @PutMapping("{id}")
    public CategoriaDTO actualizar(@PathVariable Long id, @RequestBody CategoriaCreateUpdateDTO categoriaDto){
        return service.actualizar(id, categoriaDto);
    }

    @DeleteMapping("{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}