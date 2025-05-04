package com.example.creacionusuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.creacionusuario.model.Usuario;
import com.example.creacionusuario.repository.UsuarioRepository;

@Service

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List <Usuario> buscarUsuario(){
        return usuarioRepository.findAll();

        
    }

    public Usuario buscarPorId (long id){
        return usuarioRepository.findById(id).get();

    }
    public Usuario agregarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);

    }


    public Usuario editarPorId(Long id, Usuario datosNuevos) {
        // Buscar si el usuario existe en la base de datos
        Optional<Usuario> usuarioExiste = usuarioRepository.findById(id);
    
        if (usuarioExiste.isPresent()) {
            // Obtener el usuario actual
            Usuario usuario = usuarioExiste.get();
    
            // Actualizar los datos con los nuevos (ajusta los campos según tu clase)
            usuario.setNombres(datosNuevos.getNombres());
            usuario.setApellido(datosNuevos.getApellido());
            usuario.setCorreo(datosNuevos.getCorreo());
            usuario.setNombres(datosNuevos.getNombres());
            // Agrega más setters si tu clase tiene más atributos
    
            // Guardar el usuario actualizado
            return usuarioRepository.save(usuario);
        } else {
            // Si no existe, puedes lanzar una excepción o devolver null
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    public void eliminarUsuario (long id ){
        usuarioRepository.deleteById(id);
    }
    
    

    
     

}
