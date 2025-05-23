package com.microservicios.promociones_ofertas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.promociones_ofertas.model.promocion;

@Repository
public interface promocionRepository extends JpaRepository <promocion, Long> {

}
