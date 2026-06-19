package com.andesfin.repository;

import com.andesfin.entity.ProductoFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductoFinancieroRepository extends JpaRepository<ProductoFinanciero, UUID> {

    List<ProductoFinanciero> findByActivoTrue();
}