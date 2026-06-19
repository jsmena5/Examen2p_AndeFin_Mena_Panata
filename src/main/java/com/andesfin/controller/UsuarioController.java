package com.andesfin.controller;

import com.andesfin.dto.UsuarioDTO;
import com.andesfin.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para gestionar usuarios de AndesFin")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(
            summary = "Listar usuarios",
            description = "Retorna todos los usuarios registrados en la plataforma AndesFin"
    )
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Busca un usuario específico mediante su identificador UUID"
    )
    public UsuarioDTO obtenerUsuarioPorId(@PathVariable UUID id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Crear usuario",
            description = "Registra un nuevo usuario con nombre, email y capital disponible"
    )
    public UsuarioDTO crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza los datos de un usuario existente"
    )
    public UsuarioDTO actualizarUsuario(
            @PathVariable UUID id,
            @RequestBody UsuarioDTO usuarioDTO
    ) {
        return usuarioService.actualizarUsuario(id, usuarioDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario de la base de datos"
    )
    public void eliminarUsuario(@PathVariable UUID id) {
        usuarioService.eliminarUsuario(id);
    }
}