package com.tuturno.seed.usuarios;

import com.tuturno.model.Usuario;
import com.tuturno.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuariosData {
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> getUsuarios() {
        // 1. Cliente Registrado (USER)
        Usuario cliente = new Usuario();
        cliente.setNombre("Carlos Cliente");
        cliente.setEmail("cliente@test.com");
        cliente.setPassword(passwordEncoder.encode("1234"));
        cliente.setTelefono("600111222");
        cliente.setRol(rolService.filtrarPorNombre("USER"));

        // 2. Jefe / Empleado (BOSS)
        Usuario jefe = new Usuario();
        jefe.setNombre("Laura Jefe");
        jefe.setEmail("jefe@test.com");
        jefe.setPassword(passwordEncoder.encode("1234"));
        jefe.setTelefono("600333444");
        jefe.setRol(rolService.filtrarPorNombre("BOSS"));

        // 3. Administrador (ADMIN)
        Usuario admin = new Usuario();
        admin.setNombre("Admin Sistema");
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("1234"));
        admin.setTelefono("600555666");
        admin.setRol(rolService.filtrarPorNombre("ADMIN"));

        return List.of(cliente, jefe, admin);
    }
}