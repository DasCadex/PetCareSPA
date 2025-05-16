package com.example.creacionusuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.creacionusuario.model.Rol;
import com.example.creacionusuario.repository.RoleRepositoy;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepositoy roleRepositoy;

    public List<Rol> bucarRol(){

        return roleRepositoy.findAll();
        
    }

    public Rol buscarPorId (Long id){
        return roleRepositoy.findById(id).orElseThrow(()-> new RuntimeException("Rol no encontrado"));
    }

}
