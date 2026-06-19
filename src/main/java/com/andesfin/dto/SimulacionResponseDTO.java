package com.andesfin.dto;

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
public class SimulacionResponseDTO {

    private UUID id;
    private UUID usuarioId;
    private LocalDateTime fechaSimulacion;
    private BigDecimal capitalDisponible;
    private List<ProductoSeleccionadoDTO> productosSeleccionados;
    private BigDecimal costoTotal;
    private BigDecimal capitalRestante;
    private BigDecimal gananciaTotal;
    private BigDecimal retornoTotalPorcentaje;
    private BigDecimal eficienciaCapital;
    private Integer cantidadProductos;
    private String mensaje;
}