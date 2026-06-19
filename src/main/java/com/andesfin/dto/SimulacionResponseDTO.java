package com.andesfin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta de una simulación de inversión")
public class SimulacionResponseDTO {

    @Schema(example = "f6g7h8i9-j0k1-2345-fghi-678901234567")
    private UUID id;

    @Schema(example = "11111111-1111-1111-1111-111111111111")
    private UUID usuarioId;

    private LocalDateTime fechaSimulacion;

    @Schema(example = "3000.00")
    private BigDecimal capitalDisponible;

    private List<ProductoSeleccionadoDTO> productosSeleccionados;

    @Schema(example = "2500.00")
    private BigDecimal costoTotal;

    @Schema(example = "500.00")
    private BigDecimal capitalRestante;

    @Schema(example = "265.00")
    private BigDecimal gananciaTotal;

    @Schema(example = "10.60")
    private BigDecimal retornoTotalPorcentaje;

    @Schema(example = "83.33")
    private BigDecimal eficienciaCapital;

    @Schema(example = "2")
    private Integer cantidadProductos;

    @Schema(example = "Simulación exitosa con ganancias óptimas")
    private String mensaje;
}