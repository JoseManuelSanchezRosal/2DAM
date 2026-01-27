package com.cine.entradas.controller;

import com.cine.entradas.dto.VentaCreateDTO;
import com.cine.entradas.dto.VentaResponseDTO;
import com.cine.entradas.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crear(@Valid @RequestBody VentaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.crearVenta(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerVenta(id));
    }
}