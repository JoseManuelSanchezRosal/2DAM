package com.dam2.Practica1.controllers;


import com.dam2.Practica1.dto.UsuarioDTO.UsuarioCreateUpdateDTO;
import com.dam2.Practica1.dto.UsuarioDTO.UsuarioDTO;
import com.dam2.Practica1.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor

public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public UsuarioDTO agregar(@RequestBody UsuarioCreateUpdateDTO usuarioDto){
        return service.agregar(usuarioDto);
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizar(@PathVariable Long id, @RequestBody UsuarioCreateUpdateDTO usuarioDto){
        return service.actualizar(id, usuarioDto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
