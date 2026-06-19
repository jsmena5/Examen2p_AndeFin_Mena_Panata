package com.andesfin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "simulaciones_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulacionDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulacion_id", nullable = false)
    private Simulacion simulacion;

    @Column(name = "nombre_producto", nullable = false, length = 150)
    private String nombreProducto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(precision = 5, scale = 2)
    private BigDecimal riesgo;

    @Column(name = "porcentaje_ganancia", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentajeGanancia;

    @Column(name = "ganancia_esperada", nullable = false, precision = 10, scale = 2)
    private BigDecimal gananciaEsperada;

    @Column(name = "retorno_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal retornoEstimado;
}