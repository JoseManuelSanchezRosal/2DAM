package com.cine.entradas.mapper;

import com.cine.entradas.dto.PeliculaCreateDTO;
import com.cine.entradas.dto.PeliculaResponseDTO;
import com.cine.entradas.model.Pelicula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// 'uses' permite reutilizar la lógica de los otros mappers para la respuesta
@Mapper(componentModel = "spring", uses = {DirectorMapper.class, ActorMapper.class})
public interface PeliculaMapper {

    PeliculaResponseDTO toResponseDTO(Pelicula pelicula);

    // OBLIGATORIO: Ignorar relaciones en la entrada. Se resuelven en el Service.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "director", ignore = true)
    @Mapping(target = "actores", ignore = true)
    Pelicula toEntity(PeliculaCreateDTO dto);
}