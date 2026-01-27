package com.cine.entradas.mapper;

import com.cine.entradas.dto.SalaDTO;
import com.cine.entradas.model.Sala;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalaMapper {
    SalaDTO toDTO(Sala sala);
    Sala toEntity(SalaDTO dto);
}