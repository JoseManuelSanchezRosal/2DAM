package com.tuturno.controller;

import com.tuturno.dto.cita.CitaRequestDTO;
import com.tuturno.model.Cita;
import com.tuturno.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import com.tuturno.dto.cita.SlotDto;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @GetMapping
    public ResponseEntity<List<Cita>> listar(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long realUserId = (Long) authentication.getPrincipal();
        boolean esAdmin = authentication.getAuthorities().stream()
                .anyMatch(rol -> rol.getAuthority().equals("ROLE_ADMIN"));

        System.out.println("🔎 [GET] Buscando citas para el usuario ID: " + realUserId);

        List<Cita> citas = citaService.listarSegunRol(realUserId, esAdmin);

        System.out.println("✅ [GET] Se han encontrado " + citas.size() + " citas en la base de datos.");

        citas.forEach(c -> {
            if (c.getUsuario() != null) {
                c.getUsuario().setPassword(null);
            }
        });

        return ResponseEntity.ok(citas);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CitaRequestDTO request, Authentication authentication) {
        try {
            if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            Long realUserId = (Long) authentication.getPrincipal();
            System.out.println("💾 [POST] Guardando nueva cita...");

            Cita nuevaCita = citaService.crearCita(request.fechaInicio(), realUserId, request.servicioId());

            System.out.println("🟢 [POST] Cita guardada exitosamente. ID: " + nuevaCita.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
        } catch (IllegalStateException e) {
            System.out.println("⚠️ [POST] Solapamiento detectado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("🔴 [POST] Error grave al guardar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // --- NUEVO: ENDPOINT PARA MODIFICAR ---
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody CitaRequestDTO request, Authentication authentication) {
        try {
            if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            Long realUserId = (Long) authentication.getPrincipal();
            System.out.println("✏️ [PUT] Modificando cita ID: " + id);

            Cita citaActualizada = citaService.modificarCita(id, request.fechaInicio(), realUserId, request.servicioId());

            System.out.println("🟢 [PUT] Cita modificada exitosamente.");
            return ResponseEntity.ok(citaActualizada);
        } catch (IllegalStateException e) {
            System.out.println("⚠️ [PUT] Solapamiento detectado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("🔴 [PUT] Error al modificar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/disponibles/mes")
    public ResponseEntity<List<Integer>> getDiasDisponibles(
            @RequestParam int anio, @RequestParam int mes, @RequestParam Long servicioId) {
        return ResponseEntity.ok(citaService.getDiasDisponiblesEnMes(anio, mes, servicioId));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<SlotDto>> getDisponibilidad(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam Long servicioId) {
        return ResponseEntity.ok(citaService.getHuecosDisponibles(fecha, servicioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}