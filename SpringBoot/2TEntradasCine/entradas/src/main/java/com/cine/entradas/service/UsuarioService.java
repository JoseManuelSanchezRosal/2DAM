package com.cine.entradas.service;

import com.cine.entradas.dto.UsuarioCreateDTO;
import com.cine.entradas.dto.UsuarioDTO;
import com.cine.entradas.mapper.UsuarioMapper;
import com.cine.entradas.model.Usuario;
import com.cine.entradas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final UsuarioMapper usuarioMapper;

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return usuarioRepo.findAll().stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    @Transactional
    public UsuarioDTO registrar(UsuarioCreateDTO dto) {
        // Aquí podrías validar que el email no exista ya
        Usuario usuario = usuarioMapper.toEntity(dto);
        return usuarioMapper.toDTO(usuarioRepo.save(usuario));
    }
}