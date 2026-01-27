package com.cine.entradas.mapper;

import com.cine.entradas.dto.VentaResponseDTO;
import com.cine.entradas.model.Venta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntradaMapper.class})
public interface VentaMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.email", target = "usuarioEmail")
    VentaResponseDTO toResponseDTO(Venta venta);
}