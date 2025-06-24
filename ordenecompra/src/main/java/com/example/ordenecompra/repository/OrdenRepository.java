package com.example.ordenecompra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ordenecompra.model.Orden;

public interface  OrdenRepository extends JpaRepository <Orden,Long> {
    List<Orden> findByUsuarioId(Long usuarioId);

}
