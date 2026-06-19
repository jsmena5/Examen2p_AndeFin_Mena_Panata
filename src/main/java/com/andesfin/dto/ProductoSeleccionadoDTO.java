package com.andesfin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Producto seleccionado por la simulación óptima")
public class ProductoSeleccionadoDTO {

    @Schema(example = "ETF Global")
    private String nombre;

    @Schema(example = "1500.00")
    private BigDecimal precio;

    @Schema(example = "7")
    private BigDecimal riesgo;

    @Schema(example = "12.00")
    private BigDecimal porcentajeGanancia;

    @Schema(example = "180.00")
    private BigDecimal gananciaEsperada;

    @Schema(example = "180.00")
    private BigDecimal retornoEstimado;
}