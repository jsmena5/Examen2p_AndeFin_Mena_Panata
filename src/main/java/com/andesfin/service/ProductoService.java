package com.andesfin.service;

import com.andesfin.dto.ProductoDTO;

import java.util.List;
import java.util.UUID;

public interface ProductoService {

    List<ProductoDTO> listarProductosActivos();

    ProductoDTO obtenerProductoPorId(UUID id);

    ProductoDTO crearProducto(ProductoDTO productoDTO);

    ProductoDTO actualizarProducto(UUID id, ProductoDTO productoDTO);

    void eliminarProducto(UUID id);
}