package com.dam2.Practica1.repository;

import com.dam2.Practica1.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
