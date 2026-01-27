package com.cine.entradas.mapper;

import com.cine.entradas.dto.FuncionCreateDTO;
import com.cine.entradas.dto.FuncionResponseDTO;
import com.cine.entradas.model.Funcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// USES: Reutiliza PeliculaMapper y SalaMapper para convertir los objetos anidados en la respuesta
@Mapper(componentModel = "spring", uses = {PeliculaMapper.class, SalaMapper.class})
public interface FuncionMapper {

    FuncionResponseDTO toResponseDTO(Funcion funcion);

    // INPUT: Ignoramos las relaciones complejas, las llenará el Service
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pelicula", ignore = true)
    @Mapping(target = "sala", ignore = true)
    Funcion toEntity(FuncionCreateDTO dto);
}