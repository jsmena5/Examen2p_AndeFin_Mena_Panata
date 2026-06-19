package com.andesfin.controller;

import com.andesfin.dto.SimulacionRequestDTO;
import com.andesfin.dto.SimulacionResponseDTO;
import com.andesfin.service.SimulacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/simulaciones")
@RequiredArgsConstructor
public class SimulacionController {

    private final SimulacionService simulacionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SimulacionResponseDTO simularInversion(
            @Valid @RequestBody SimulacionRequestDTO requestDTO
    ) {
        return simulacionService.simularInversion(requestDTO);
    }

    @GetMapping("/{usuarioId}")
    public List<SimulacionResponseDTO> listarSimulacionesPorUsuario(
            @PathVariable UUID usuarioId
    ) {
        return simulacionService.listarSimulacionesPorUsuario(usuarioId);
    }
}