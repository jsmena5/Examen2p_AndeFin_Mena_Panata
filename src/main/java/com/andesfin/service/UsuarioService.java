package com.andesfin.service;

import com.andesfin.dto.UsuarioDTO;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {

    List<UsuarioDTO> listarUsuarios();

    UsuarioDTO obtenerUsuarioPorId(UUID id);

    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO actualizarUsuario(UUID id, UsuarioDTO usuarioDTO);

    void eliminarUsuario(UUID id);
}