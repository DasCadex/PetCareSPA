package com.microservicios.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.pagos.model.Pagos;

@Repository
public interface RepositoryPagos extends JpaRepository <Pagos , Long> {


    




}
