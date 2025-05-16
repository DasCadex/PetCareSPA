package com.microservicios.mascotas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.mascotas.model.Mascotas;

@Repository
public interface MascotasRepository extends JpaRepository<Mascotas,Long >  {

    
}
