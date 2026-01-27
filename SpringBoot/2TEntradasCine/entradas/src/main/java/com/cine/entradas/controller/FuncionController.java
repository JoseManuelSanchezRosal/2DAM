package com.cine.entradas.controller;

import com.cine.entradas.dto.FuncionCreateDTO;
import com.cine.entradas.dto.FuncionResponseDTO;
import com.cine.entradas.service.FuncionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funciones")
@RequiredArgsConstructor
public class FuncionController {

    private final FuncionService funcionService;

    @GetMapping
    public ResponseEntity<List<FuncionResponseDTO>> getAll() {
        return ResponseEntity.ok(funcionService.findAll());
    }

    @PostMapping
    public ResponseEntity<FuncionResponseDTO> create(@Valid @RequestBody FuncionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionService.create(dto));
    }
}