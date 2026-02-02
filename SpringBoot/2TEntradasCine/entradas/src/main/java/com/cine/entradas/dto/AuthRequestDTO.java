package com.cine.entradas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}