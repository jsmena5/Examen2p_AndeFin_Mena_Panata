package com.andesfin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Solicitud para realizar una simulación de inversión")
public class SimulacionRequestDTO {

    @NotNull(message = "El usuario_id es obligatorio")
    @Schema(
            description = "Identificador UUID del usuario que realiza la simulación",
            example = "11111111-1111-1111-1111-111111111111"
    )
    private UUID usuarioId;

    @NotNull(message = "El capital disponible es obligatorio")
    @DecimalMin(value = "0.01", message = "El capital disponible debe ser mayor a 0")
    @Schema(
            description = "Capital disponible para invertir",
            example = "3000.00"
    )
    private BigDecimal capitalDisponible;

    @Valid
    @NotEmpty(message = "Debe enviar al menos un producto para simular")
    @Schema(description = "Lista de productos candidatos para la simulación")
    private List<ProductoSimulacionDTO> productos;
}