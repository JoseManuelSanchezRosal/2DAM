package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.UsuarioDTO.UsuarioCreateUpdateDTO;
import com.dam2.Practica1.dto.UsuarioDTO.UsuarioDTO;
import com.dam2.Practica1.models.Usuario;
import com.dam2.Practica1.repository.UsuarioRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Getter

public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private UsuarioDTO toDTO(Usuario u){
        return new UsuarioDTO(
                u.getId(),
                u.getUserName(),
                u.getEmail(),
                u.getPassword(),
                u.getRol()
        );
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public UsuarioDTO agregar(UsuarioCreateUpdateDTO usuarioDto) {
        Usuario u  = new Usuario();
        u.setUserName(usuarioDto.getUserName());
        u.setEmail(usuarioDto.getEmail());
        u.setPassword(usuarioDto.getPassword());
        u.setRol(usuarioDto.getRol());

        usuarioRepository.save(u);

        return toDTO(u);
    }

    public UsuarioDTO actualizar(Long id, UsuarioCreateUpdateDTO usuarioDto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()){
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario u = optionalUsuario.get();
        u.setUserName(usuarioDto.getUserName());
        u.setEmail(usuarioDto.getEmail());
        u.setPassword(usuarioDto.getPassword());
        u.setRol(usuarioDto.getRol());

        usuarioRepository.save(u);

        return toDTO(u);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
