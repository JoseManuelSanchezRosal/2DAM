package com.cine.entradas.service;

import com.cine.entradas.dto.DetalleEntradaDTO;
import com.cine.entradas.dto.VentaCreateDTO;
import com.cine.entradas.dto.VentaResponseDTO;
import com.cine.entradas.mapper.VentaMapper;
import com.cine.entradas.model.*;
import com.cine.entradas.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepo;
    private final UsuarioRepository usuarioRepo;
    private final FuncionRepository funcionRepo;
    private final EntradaRepository entradaRepo; // Opcional si usas cascade
    private final VentaMapper ventaMapper;

    @Transactional
    public VentaResponseDTO crearVenta(VentaCreateDTO dto) {
        // 1. Buscar Usuario
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // 2. Crear Objeto Venta base
        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("COMPLETADA");
        venta.setEntradas(new ArrayList<>()); // Inicializar lista

        double totalCalculado = 0.0;

        // 3. Procesar cada entrada solicitada
        for (DetalleEntradaDTO detalle : dto.getEntradas()) {
            Funcion funcion = funcionRepo.findById(detalle.getFuncionId())
                    .orElseThrow(() -> new EntityNotFoundException("Función no encontrada ID: " + detalle.getFuncionId()));

            // Aquí podrías validar si el asiento ya está ocupado...

            Entrada entrada = new Entrada();
            entrada.setFila(detalle.getFila());
            entrada.setAsiento(detalle.getAsiento());
            entrada.setEstado("ACTIVA");
            entrada.setFuncion(funcion);
            entrada.setVenta(venta); // Vincular bidireccional

            venta.getEntradas().add(entrada);
            totalCalculado += funcion.getPrecio();
        }

        venta.setImporteTotal(totalCalculado);

        // 4. Guardar todo (Gracias al CascadeType.ALL en Venta, se guardan las entradas solas)
        Venta ventaGuardada = ventaRepo.save(venta);

        return ventaMapper.toResponseDTO(ventaGuardada);

    }
    // ... métodos anteriores ...

    @Transactional(readOnly = true)
    public VentaResponseDTO obtenerVenta(Long id) {
        Venta venta = ventaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada con ID: " + id));
        return ventaMapper.toResponseDTO(venta);
    }
}