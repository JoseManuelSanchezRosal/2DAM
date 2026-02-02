package com.cine.entradas.service;

import com.cine.entradas.dto.UsuarioCreateDTO;
import com.cine.entradas.dto.UsuarioDTO;
import com.cine.entradas.mapper.UsuarioMapper;
import com.cine.entradas.model.Usuario;
import com.cine.entradas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // IMPORTANTE
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder; // 1. Inyectamos el codificador

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return usuarioRepo.findAll().stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    @Transactional
    public UsuarioDTO registrar(UsuarioCreateDTO dto) {
        // Validación básica: verificar si el email ya existe
        if (usuarioRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("El email " + dto.getEmail() + " ya está registrado.");
        }

        Usuario usuario = usuarioMapper.toEntity(dto);

        // 2. ENCRIPTAR LA CONTRASEÑA ANTES DE GUARDAR
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        return usuarioMapper.toDTO(usuarioRepo.save(usuario));
    }
}