package com.cine.entradas.mapper;

import com.cine.entradas.dto.*;
import com.cine.entradas.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    ActorDTO toDTO(Actor actor);
    Actor toEntity(ActorDTO dto);
}