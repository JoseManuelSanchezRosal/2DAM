package com.cine.entradas.service;

import com.cine.entradas.dto.SalaDTO;
import com.cine.entradas.mapper.SalaMapper;
import com.cine.entradas.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaService {
    private final SalaRepository salaRepo;
    private final SalaMapper salaMapper;

    @Transactional(readOnly = true)
    public List<SalaDTO> findAll() {
        return salaRepo.findAll().stream().map(salaMapper::toDTO).toList();
    }
}