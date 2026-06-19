package com.andesfin.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoSeleccionadoDTO {

    private String nombre;
    private BigDecimal precio;
    private BigDecimal riesgo;
    private BigDecimal porcentajeGanancia;
    private BigDecimal gananciaEsperada;
    private BigDecimal retornoEstimado;
}