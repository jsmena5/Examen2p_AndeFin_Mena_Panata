package com.andesfin.service.impl;

import com.andesfin.dto.ProductoDTO;
import com.andesfin.entity.ProductoFinanciero;
import com.andesfin.repository.ProductoFinancieroRepository;
import com.andesfin.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoFinancieroRepository productoRepository;

    @Override
    public List<ProductoDTO> listarProductosActivos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ProductoDTO obtenerProductoPorId(UUID id) {
        ProductoFinanciero producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        return toDTO(producto);
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        ProductoFinanciero producto = ProductoFinanciero.builder()
                .nombre(productoDTO.getNombre())
                .descripcion(productoDTO.getDescripcion())
                .costo(productoDTO.getCosto())
                .porcentajeRetorno(productoDTO.getPorcentajeRetorno())
                .activo(productoDTO.getActivo() != null ? productoDTO.getActivo() : true)
                .build();

        return toDTO(productoRepository.save(producto));
    }

    @Override
    public ProductoDTO actualizarProducto(UUID id, ProductoDTO productoDTO) {
        ProductoFinanciero producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setCosto(productoDTO.getCosto());
        producto.setPorcentajeRetorno(productoDTO.getPorcentajeRetorno());
        producto.setActivo(productoDTO.getActivo());

        return toDTO(productoRepository.save(producto));
    }

    @Override
    public void eliminarProducto(UUID id) {
        ProductoFinanciero producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private ProductoDTO toDTO(ProductoFinanciero producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .costo(producto.getCosto())
                .porcentajeRetorno(producto.getPorcentajeRetorno())
                .activo(producto.getActivo())
                .build();
    }
}