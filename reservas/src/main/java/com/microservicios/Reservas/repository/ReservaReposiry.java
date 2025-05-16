package com.microservicios.Reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.Reservas.model.Reservas;


import java.util.List;

@Repository
public interface ReservaReposiry extends JpaRepository<Reservas, Long>{
    List<Reservas> findByEstado(String estado);

}
