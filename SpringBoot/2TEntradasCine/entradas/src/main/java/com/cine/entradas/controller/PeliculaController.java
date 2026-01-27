package com.cine.entradas.controller;

import com.cine.entradas.dto.PeliculaCreateDTO;
import com.cine.entradas.dto.PeliculaResponseDTO;
import com.cine.entradas.service.PeliculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
@RequiredArgsConstructor
public class PeliculaController {

    private final PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<PeliculaResponseDTO>> getAll() {
        return ResponseEntity.ok(peliculaService.findAll());
    }

    @PostMapping
    public ResponseEntity<PeliculaResponseDTO> create(@Valid @RequestBody PeliculaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaService.create(dto));
    }
}