package com.cine.entradas.mapper;

import com.cine.entradas.dto.EntradaResponseDTO;
import com.cine.entradas.model.Entrada;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntradaMapper {

    // Extraemos datos anidados para aplanar la respuesta (Flattening)
    @Mapping(source = "funcion.pelicula.titulo", target = "tituloPelicula")
    @Mapping(source = "funcion.sala.nombre", target = "nombreSala")
    @Mapping(source = "funcion.precio", target = "precio")
    EntradaResponseDTO toResponseDTO(Entrada entrada);
}