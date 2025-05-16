package com.example.creacionusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.creacionusuario.model.Usuario;

@Repository

public interface UsuarioRepository extends JpaRepository <Usuario,Long> {

}
