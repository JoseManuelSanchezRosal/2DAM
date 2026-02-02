package com.cine.entradas.mapper;

import com.cine.entradas.dto.SalaDTO;
import com.cine.entradas.model.Sala;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SalaMapper {
    SalaDTO toDTO(Sala sala);

    @Mapping(target = "funciones", ignore = true) // Ignoramos la lista al crear desde DTO
    Sala toEntity(SalaDTO dto);
}