package com.cine.entradas.mapper;

import com.cine.entradas.dto.*;
import com.cine.entradas.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    DirectorDTO toDTO(Director director);
    Director toEntity(DirectorDTO dto);
}
