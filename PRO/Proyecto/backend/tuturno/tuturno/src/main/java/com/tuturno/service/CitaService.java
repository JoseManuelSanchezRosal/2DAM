package com.tuturno.service;

import com.tuturno.model.Cita;
import com.tuturno.model.Servicio;
import com.tuturno.model.Usuario;
import com.tuturno.repository.CitaRepository;
import com.tuturno.repository.ServicioRepository;
import com.tuturno.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;

    public List<Cita> listarSegunRol(String email, boolean esAdmin) {
        if (esAdmin) {
            return citaRepository.findAll();
        } else {
            return citaRepository.findByUsuarioEmail(email);
        }
    }

    public Cita crearCita(LocalDateTime fechaInicio, Long usuarioId, Long servicioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        LocalDateTime fechaFin = fechaInicio.plusMinutes(servicio.getDuracionMinutos() + 15);

        List<Cita> citasDelDia = citaRepository.findByFecha(fechaInicio.toLocalDate());
        for (Cita c : citasDelDia) {
            LocalDateTime cInicio = LocalDateTime.of(c.getFecha(), LocalTime.of(c.getHora(), c.getMinutos(), c.getSegundos()));
            int duracionCita = 15;
            if (c.getServicios() != null) {
                duracionCita += c.getServicios().stream().mapToInt(Servicio::getDuracionMinutos).sum();
            }
            LocalDateTime cFin = cInicio.plusMinutes(duracionCita);

            if (fechaInicio.isBefore(cFin) && fechaFin.isAfter(cInicio)) {
                throw new IllegalArgumentException("El tramo horario seleccionado ya se encuentra ocupado. Por favor, elige otro.");
            }
        }

        Cita cita = new Cita();
        cita.setUsuario(usuario);
        cita.setFecha(fechaInicio.toLocalDate());
        cita.setHora(fechaInicio.getHour());
        cita.setMinutos(fechaInicio.getMinute());
        cita.setSegundos(fechaInicio.getSecond());
        cita.setServicios(List.of(servicio));

        return citaRepository.save(cita);
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }
}