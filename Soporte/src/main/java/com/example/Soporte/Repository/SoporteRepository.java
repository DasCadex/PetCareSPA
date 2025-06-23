package com.example.Soporte.Repository;

import com.example.Soporte.Model.Soporte;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Long> {
    List<Soporte> findByUsuarioId(Long usuarioId); // Método de filtro por usuario

   
}
