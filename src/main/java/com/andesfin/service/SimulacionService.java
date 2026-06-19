package com.andesfin.service;

import com.andesfin.dto.SimulacionRequestDTO;
import com.andesfin.dto.SimulacionResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SimulacionService {

    SimulacionResponseDTO simularInversion(SimulacionRequestDTO requestDTO);

    List<SimulacionResponseDTO> listarSimulacionesPorUsuario(UUID usuarioId);
}