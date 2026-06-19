package com.andesfin.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

    private UUID id;
    private String nombre;
    private String descripcion;
    private BigDecimal costo;
    private BigDecimal porcentajeRetorno;
    private Boolean activo;
}