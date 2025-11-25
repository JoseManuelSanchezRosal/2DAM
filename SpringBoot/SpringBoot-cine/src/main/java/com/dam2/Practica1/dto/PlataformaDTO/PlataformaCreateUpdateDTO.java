package com.dam2.Practica1.dto.PlataformaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PlataformaCreateUpdateDTO {
    private String nombre;
    private String url;

}
