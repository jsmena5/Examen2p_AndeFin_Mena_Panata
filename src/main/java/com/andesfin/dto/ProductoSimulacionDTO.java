package com.andesfin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Producto candidato enviado para la simulación")
public class ProductoSimulacionDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Schema(
            description = "Nombre del producto financiero",
            example = "ETF Global"
    )
    private String nombre;

    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
    @Schema(
            description = "Precio o costo mínimo del producto",
            example = "1500.00"
    )
    private BigDecimal precio;

    @NotNull(message = "El porcentaje de ganancia es obligatorio")
    @DecimalMin(value = "0.00", message = "El porcentaje de ganancia no puede ser negativo")
    @Schema(
            description = "Porcentaje de ganancia estimada del producto",
            example = "12.00"
    )
    private BigDecimal porcentajeGanancia;

    @Schema(
            description = "Nivel de riesgo estimado del producto",
            example = "7"
    )
    private BigDecimal riesgo;
}