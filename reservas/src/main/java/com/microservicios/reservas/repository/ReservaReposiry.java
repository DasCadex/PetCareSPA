package com.microservicios.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.reservas.model.Reservas;



@Repository
public interface ReservaReposiry extends JpaRepository<Reservas, Long>{
    

}
