package com.andesfin.repository;

import com.andesfin.entity.SimulacionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SimulacionDetalleRepository extends JpaRepository<SimulacionDetalle, UUID> {
}