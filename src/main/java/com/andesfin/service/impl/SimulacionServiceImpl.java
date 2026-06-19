package com.andesfin.service.impl;

import com.andesfin.dto.*;
import com.andesfin.entity.Simulacion;
import com.andesfin.entity.SimulacionDetalle;
import com.andesfin.entity.Usuario;
import com.andesfin.repository.SimulacionRepository;
import com.andesfin.repository.UsuarioRepository;
import com.andesfin.service.SimulacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SimulacionServiceImpl implements SimulacionService {

    private final SimulacionRepository simulacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public SimulacionResponseDTO simularInversion(SimulacionRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + requestDTO.getUsuarioId()));

        List<ProductoSimulacionDTO> productos = requestDTO.getProductos();
        BigDecimal capitalDisponible = requestDTO.getCapitalDisponible();

        validarProductos(productos);

        ProductoSimulacionDTO productoMasBarato = obtenerProductoMasBarato(productos);

        if (productoMasBarato.getPrecio().compareTo(capitalDisponible) > 0) {
            BigDecimal diferenciaNecesaria = productoMasBarato.getPrecio().subtract(capitalDisponible);

            throw new RuntimeException(
                    "Fondos insuficientes. Capital disponible: " + capitalDisponible
                            + ". Producto más barato: " + productoMasBarato.getPrecio()
                            + ". Diferencia necesaria: " + diferenciaNecesaria
            );
        }

        List<ProductoSimulacionDTO> mejorCombinacion = obtenerMejorCombinacion(productos, capitalDisponible);

        BigDecimal costoTotal = calcularCostoTotal(mejorCombinacion);
        BigDecimal gananciaTotal = calcularGananciaTotal(mejorCombinacion);
        BigDecimal capitalRestante = capitalDisponible.subtract(costoTotal);

        BigDecimal retornoTotalPorcentaje = calcularRetornoTotalPorcentaje(gananciaTotal, costoTotal);
        BigDecimal eficienciaCapital = calcularEficienciaCapital(costoTotal, capitalDisponible);

        String mensaje = generarMensaje(gananciaTotal, eficienciaCapital);

        Simulacion simulacion = Simulacion.builder()
                .usuario(usuario)
                .fechaSimulacion(LocalDateTime.now())
                .capitalDisponible(capitalDisponible)
                .costoTotal(costoTotal)
                .capitalRestante(capitalRestante)
                .gananciaTotal(gananciaTotal)
                .retornoTotalPorcentaje(retornoTotalPorcentaje)
                .eficienciaCapital(eficienciaCapital)
                .mensaje(mensaje)
                .detalles(new ArrayList<>())
                .build();

        for (ProductoSimulacionDTO producto : mejorCombinacion) {
            BigDecimal gananciaEsperada = calcularGananciaProducto(producto);
            BigDecimal retornoEstimado = gananciaEsperada;

            SimulacionDetalle detalle = SimulacionDetalle.builder()
                    .simulacion(simulacion)
                    .nombreProducto(producto.getNombre())
                    .precio(producto.getPrecio())
                    .riesgo(producto.getRiesgo())
                    .porcentajeGanancia(producto.getPorcentajeGanancia())
                    .gananciaEsperada(gananciaEsperada)
                    .retornoEstimado(retornoEstimado)
                    .build();

            simulacion.getDetalles().add(detalle);
        }

        Simulacion simulacionGuardada = simulacionRepository.save(simulacion);

        return toResponseDTO(simulacionGuardada);
    }

    @Override
    public List<SimulacionResponseDTO> listarSimulacionesPorUsuario(UUID usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }

        return simulacionRepository.findByUsuarioIdOrderByFechaSimulacionDesc(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private void validarProductos(List<ProductoSimulacionDTO> productos) {
        for (ProductoSimulacionDTO producto : productos) {
            if (producto.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("El precio no puede ser negativo: " + producto.getNombre());
            }

            if (producto.getPorcentajeGanancia().compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("El porcentaje de ganancia no puede ser negativo: " + producto.getNombre());
            }
        }
    }

    private ProductoSimulacionDTO obtenerProductoMasBarato(List<ProductoSimulacionDTO> productos) {
        return productos.stream()
                .filter(producto -> producto.getPrecio().compareTo(BigDecimal.ZERO) > 0)
                .min((p1, p2) -> p1.getPrecio().compareTo(p2.getPrecio()))
                .orElseThrow(() -> new RuntimeException("No existen productos con precio válido para simular"));
    }

    private List<ProductoSimulacionDTO> obtenerMejorCombinacion(
            List<ProductoSimulacionDTO> productos,
            BigDecimal capitalDisponible
    ) {
        List<ProductoSimulacionDTO> mejorCombinacion = new ArrayList<>();
        BigDecimal[] mejorGanancia = {BigDecimal.ZERO};
        BigDecimal[] mejorCosto = {BigDecimal.ZERO};

        int totalCombinaciones = 1 << productos.size();

        for (int i = 1; i < totalCombinaciones; i++) {
            List<ProductoSimulacionDTO> combinacionActual = new ArrayList<>();

            for (int j = 0; j < productos.size(); j++) {
                if ((i & (1 << j)) != 0) {
                    combinacionActual.add(productos.get(j));
                }
            }

            BigDecimal costoActual = calcularCostoTotal(combinacionActual);
            BigDecimal gananciaActual = calcularGananciaTotal(combinacionActual);

            boolean esViable = costoActual.compareTo(capitalDisponible) <= 0;

            boolean tieneMayorGanancia = gananciaActual.compareTo(mejorGanancia[0]) > 0;

            boolean mismaGananciaPeroUsaMasCapital =
                    gananciaActual.compareTo(mejorGanancia[0]) == 0
                            && costoActual.compareTo(mejorCosto[0]) > 0;

            if (esViable && (tieneMayorGanancia || mismaGananciaPeroUsaMasCapital)) {
                mejorGanancia[0] = gananciaActual;
                mejorCosto[0] = costoActual;
                mejorCombinacion = combinacionActual;
            }
        }

        return mejorCombinacion;
    }

    private BigDecimal calcularCostoTotal(List<ProductoSimulacionDTO> productos) {
        return productos.stream()
                .map(ProductoSimulacionDTO::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularGananciaTotal(List<ProductoSimulacionDTO> productos) {
        return productos.stream()
                .map(this::calcularGananciaProducto)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularGananciaProducto(ProductoSimulacionDTO producto) {
        return producto.getPrecio()
                .multiply(producto.getPorcentajeGanancia())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularRetornoTotalPorcentaje(BigDecimal gananciaTotal, BigDecimal costoTotal) {
        if (costoTotal.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return gananciaTotal
                .multiply(BigDecimal.valueOf(100))
                .divide(costoTotal, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularEficienciaCapital(BigDecimal costoTotal, BigDecimal capitalDisponible) {
        if (capitalDisponible.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        return costoTotal
                .multiply(BigDecimal.valueOf(100))
                .divide(capitalDisponible, 2, RoundingMode.HALF_UP);
    }

    private String generarMensaje(BigDecimal gananciaTotal, BigDecimal eficienciaCapital) {
        if (gananciaTotal.compareTo(BigDecimal.valueOf(20)) < 0) {
            return "Simulación con ganancias mínimas. Considere aumentar capital para mejores opciones.";
        }

        if (eficienciaCapital.compareTo(BigDecimal.valueOf(90)) >= 0) {
            return "Simulación óptima con alta eficiencia de capital";
        }

        return "Simulación exitosa con ganancias óptimas";
    }

    private SimulacionResponseDTO toResponseDTO(Simulacion simulacion) {
        List<ProductoSeleccionadoDTO> productosSeleccionados = simulacion.getDetalles()
                .stream()
                .map(detalle -> ProductoSeleccionadoDTO.builder()
                        .nombre(detalle.getNombreProducto())
                        .precio(detalle.getPrecio())
                        .riesgo(detalle.getRiesgo())
                        .porcentajeGanancia(detalle.getPorcentajeGanancia())
                        .gananciaEsperada(detalle.getGananciaEsperada())
                        .retornoEstimado(detalle.getRetornoEstimado())
                        .build())
                .toList();

        return SimulacionResponseDTO.builder()
                .id(simulacion.getId())
                .usuarioId(simulacion.getUsuario().getId())
                .fechaSimulacion(simulacion.getFechaSimulacion())
                .capitalDisponible(simulacion.getCapitalDisponible())
                .productosSeleccionados(productosSeleccionados)
                .costoTotal(simulacion.getCostoTotal())
                .capitalRestante(simulacion.getCapitalRestante())
                .gananciaTotal(simulacion.getGananciaTotal())
                .retornoTotalPorcentaje(simulacion.getRetornoTotalPorcentaje())
                .eficienciaCapital(simulacion.getEficienciaCapital())
                .cantidadProductos(productosSeleccionados.size())
                .mensaje(simulacion.getMensaje())
                .build();
    }
}