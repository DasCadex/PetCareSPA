package com.example.creacionusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.creacionusuario.model.Rol;

@Repository

public interface  RoleRepositoy extends JpaRepository <Rol,Long> {

}
