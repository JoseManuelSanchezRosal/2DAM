package com.tuturno.seed;

import com.tuturno.repository.RolRepository;
import com.tuturno.repository.ServicioRepository;
import com.tuturno.repository.UsuarioRepository;
import com.tuturno.seed.roles.RolesData;
import com.tuturno.seed.servicios.ServiciosData;
import com.tuturno.seed.usuarios.UsuariosData;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UsuariosData usuariosData;
    private final ServiciosData serviciosData;
    private final RolesData rolesData;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;
    private final RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.count() == 0) {
            this.rolRepository.saveAllAndFlush(rolesData.getRoles());
        }

        if (usuarioRepository.count() == 0) {
            this.usuarioRepository.saveAllAndFlush(usuariosData.getUsuarios());
        }

        if (servicioRepository.count() == 0) {
            this.servicioRepository.saveAllAndFlush(serviciosData.getServicios());
        }
    }
}