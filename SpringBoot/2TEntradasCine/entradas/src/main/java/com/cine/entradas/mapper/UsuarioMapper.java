package com.cine.entradas.mapper;

import com.cine.entradas.dto.UsuarioCreateDTO;
import com.cine.entradas.dto.UsuarioDTO;
import com.cine.entradas.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDTO toDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ventas", ignore = true)
    Usuario toEntity(UsuarioCreateDTO dto);
}