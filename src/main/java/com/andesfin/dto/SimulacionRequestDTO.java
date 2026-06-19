package com.andesfin.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulacionRequestDTO {

    @NotNull(message = "El usuario_id es obligatorio")
    private UUID usuarioId;

    @NotNull(message = "El capital disponible es obligatorio")
    @DecimalMin(value = "0.01", message = "El capital disponible debe ser mayor a 0")
    private BigDecimal capitalDisponible;

    @Valid
    @NotEmpty(message = "Debe enviar al menos un producto para simular")
    private List<ProductoSimulacionDTO> productos;
}