package com.andesfin.repository;

import com.andesfin.entity.Simulacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SimulacionRepository extends JpaRepository<Simulacion, UUID> {

    List<Simulacion> findByUsuarioIdOrderByFechaSimulacionDesc(UUID usuarioId);
}