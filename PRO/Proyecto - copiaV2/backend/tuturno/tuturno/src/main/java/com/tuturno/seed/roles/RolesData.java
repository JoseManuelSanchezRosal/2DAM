package com.tuturno.seed.roles;

import com.tuturno.model.Rol;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesData {
    public List<Rol> getRoles() {
        Rol user = new Rol();
        user.setNombre("USER"); // Antiguo "cliente"

        Rol boss = new Rol();
        boss.setNombre("BOSS"); // Nuevo rol para el jefe/empleado

        Rol admin = new Rol();
        admin.setNombre("ADMIN"); // Administrador del sistema

        return List.of(user, boss, admin);
    }
}