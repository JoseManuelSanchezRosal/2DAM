package com.cine.entradas.dto;
import lombok.Data;

@Data
public class DetalleEntradaDTO {
    private Long funcionId;
    private int fila;
    private int asiento;
}