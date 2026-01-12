package com.tuturno;

import com.tuturno.model.Cita;
import com.tuturno.model.Servicio;
import com.tuturno.model.Usuario;
import com.tuturno.repository.CitaRepository; // IMPORTANTE
import com.tuturno.repository.ServicioRepository;
import com.tuturno.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;
    private final CitaRepository citaRepository; // Necesitas inyectar esto
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // --- 1. GARANTIZAR QUE EXISTE UN SERVICIO ---
        Servicio servicioEstandar = null;
        if (servicioRepository.count() == 0) {
            Servicio corte = new Servicio();
            corte.setNombre("Corte Caballero");
            corte.setDescripcion("Corte clásico");
            corte.setPrecio(new BigDecimal("15.00"));
            corte.setDuracion(30);
            corte.setActivo(true);
            servicioEstandar = servicioRepository.save(corte);
        } else {
            servicioEstandar = servicioRepository.findAll().get(0);
        }

        // --- 2. CREAR USUARIO 1 y SU CITA ---
        if (usuarioRepository.findByEmail("usuario1@test.com").isEmpty()) {
            Usuario u1 = new Usuario();
            u1.setNombre("Usuario Uno");
            u1.setEmail("usuario1@test.com");
            u1.setPassword(passwordEncoder.encode("1234"));
            u1.setTelefono("666111111");
            u1.setRol("CLIENTE"); // Rol normal
            u1 = usuarioRepository.save(u1);

            // Crear Cita para Usuario 1
            Cita cita1 = new Cita();
            cita1.setUsuario(u1);
            cita1.setServicio(servicioEstandar);
            cita1.setFechaHoraInicio(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0)); // Mañana a las 10:00
            cita1.setFechaHoraFin(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30));
            citaRepository.save(cita1);

            System.out.println(">>> Creado usuario1 y su cita.");
        }

        // --- 3. CREAR USUARIO 2 y SU CITA ---
        if (usuarioRepository.findByEmail("usuario2@test.com").isEmpty()) {
            Usuario u2 = new Usuario();
            u2.setNombre("Usuario Dos");
            u2.setEmail("usuario2@test.com");
            u2.setPassword(passwordEncoder.encode("1234"));
            u2.setTelefono("666222222");
            u2.setRol("CLIENTE"); // Rol normal
            u2 = usuarioRepository.save(u2);

            // Crear Cita para Usuario 2
            Cita cita2 = new Cita();
            cita2.setUsuario(u2);
            cita2.setServicio(servicioEstandar);
            cita2.setFechaHoraInicio(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0)); // Mañana a las 11:00
            cita2.setFechaHoraFin(LocalDateTime.now().plusDays(1).withHour(11).withMinute(30));
            citaRepository.save(cita2);

            System.out.println(">>> Creado usuario2 y su cita.");
        }

        // --- 4. CREAR ADMINISTRADOR ---
        if (usuarioRepository.findByEmail("admin@test.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Jefe Supremo");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setTelefono("600000000");
            admin.setRol("ADMIN"); // Rol Admin
            usuarioRepository.save(admin);
            System.out.println(">>> Creado admin.");
        }
    }
}