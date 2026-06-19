package com.andesfin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "simulaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Simulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_simulacion", nullable = false)
    private LocalDateTime fechaSimulacion;

    @Column(name = "capital_disponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal capitalDisponible;

    @Column(name = "costo_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoTotal;

    @Column(name = "capital_restante", nullable = false, precision = 10, scale = 2)
    private BigDecimal capitalRestante;

    @Column(name = "ganancia_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal gananciaTotal;

    @Column(name = "retorno_total_porcentaje", nullable = false, precision = 5, scale = 2)
    private BigDecimal retornoTotalPorcentaje;

    @Column(name = "eficiencia_capital", nullable = false, precision = 5, scale = 2)
    private BigDecimal eficienciaCapital;

    @Column(length = 255)
    private String mensaje;

    @OneToMany(mappedBy = "simulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SimulacionDetalle> detalles = new ArrayList<>();
}