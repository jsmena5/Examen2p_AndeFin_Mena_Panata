package com.andesfin.controller;

import com.andesfin.dto.ProductoDTO;
import com.andesfin.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> listarProductosActivos() {
        return productoService.listarProductosActivos();
    }

    @GetMapping("/{id}")
    public ProductoDTO obtenerProductoPorId(@PathVariable UUID id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }

    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(
            @PathVariable UUID id,
            @RequestBody ProductoDTO productoDTO
    ) {
        return productoService.actualizarProducto(id, productoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProducto(@PathVariable UUID id) {
        productoService.eliminarProducto(id);
    }
}