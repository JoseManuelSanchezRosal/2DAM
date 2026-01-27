package com.cine.entradas.dto;
import lombok.Data;

@Data
public class EntradaResponseDTO {
    private Long id;
    private int fila;
    private int asiento;
    private String tituloPelicula; // Muy útil para el frontend
    private String nombreSala;
    private double precio;
}