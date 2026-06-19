package com.andesfin.dto;

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
public class ProductoSimulacionDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
    private BigDecimal precio;

    @NotNull(message = "El porcentaje de ganancia es obligatorio")
    @DecimalMin(value = "0.00", message = "El porcentaje de ganancia no puede ser negativo")
    private BigDecimal porcentajeGanancia;

    private BigDecimal riesgo;
}