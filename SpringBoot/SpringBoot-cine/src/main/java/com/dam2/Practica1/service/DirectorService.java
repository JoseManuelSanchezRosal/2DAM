package com.dam2.Practica1.service;

import com.dam2.Practica1.dto.DirectorDTO.DirectorDTO;
import com.dam2.Practica1.models.Director;
import com.dam2.Practica1.repository.DirectorRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter

public class DirectorService {
    @Autowired
    DirectorRepository directorRepository;

    private DirectorDTO toDTO(Director d){
        return new DirectorDTO(
                d.getId(),
                d.getNombre()
        );
    }

}
