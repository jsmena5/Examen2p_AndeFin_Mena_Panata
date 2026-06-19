package com.andesfin.controller;

import com.andesfin.dto.ProductoDTO;
import com.andesfin.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@Tag(name = "Productos financieros", description = "Endpoints para consultar y gestionar productos financieros")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(
            summary = "Listar productos activos",
            description = "Retorna todos los productos financieros activos disponibles para simulación"
    )
    public List<ProductoDTO> listarProductosActivos() {
        return productoService.listarProductosActivos();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener producto por ID",
            description = "Busca un producto financiero específico mediante su UUID"
    )
    public ProductoDTO obtenerProductoPorId(@PathVariable UUID id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Crear producto financiero",
            description = "Registra un nuevo producto financiero en el catálogo de AndesFin"
    )
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.crearProducto(productoDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar producto financiero",
            description = "Actualiza la información de un producto financiero existente"
    )
    public ProductoDTO actualizarProducto(
            @PathVariable UUID id,
            @RequestBody ProductoDTO productoDTO
    ) {
        return productoService.actualizarProducto(id, productoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Desactivar producto financiero",
            description = "Desactiva un producto financiero para que ya no aparezca como activo"
    )
    public void eliminarProducto(@PathVariable UUID id) {
        productoService.eliminarProducto(id);
    }
}