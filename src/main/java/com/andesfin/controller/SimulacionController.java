package com.andesfin.controller;

import com.andesfin.dto.SimulacionRequestDTO;
import com.andesfin.dto.SimulacionResponseDTO;
import com.andesfin.service.SimulacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/simulaciones")
@RequiredArgsConstructor
@Tag(name = "Simulaciones", description = "Endpoints para realizar y consultar simulaciones de inversión")
public class SimulacionController {

    private final SimulacionService simulacionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Realizar simulación de inversión",
            description = "Evalúa combinaciones de productos financieros y selecciona la mejor opción según el capital disponible"
    )
    public SimulacionResponseDTO simularInversion(
            @Valid @RequestBody SimulacionRequestDTO requestDTO
    ) {
        return simulacionService.simularInversion(requestDTO);
    }

    @GetMapping("/{usuarioId}")
    @Operation(
            summary = "Consultar simulaciones por usuario",
            description = "Retorna el historial de simulaciones realizadas por un usuario específico"
    )
    public List<SimulacionResponseDTO> listarSimulacionesPorUsuario(
            @PathVariable UUID usuarioId
    ) {
        return simulacionService.listarSimulacionesPorUsuario(usuarioId);
    }
}