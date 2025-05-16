package com.example.creacionusuario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.creacionusuario.model.Rol;
import com.example.creacionusuario.model.Usuario;
import com.example.creacionusuario.repository.RoleRepositoy;
import com.example.creacionusuario.repository.UsuarioRepository;
import com.example.creacionusuario.dto.UsuarioDTO;

@Service
@Transactional

public class UsuarioService {

    @Autowired
    private RoleRepositoy roleRepositoy;

    @Autowired
    private UsuarioRepository usuarioRepository;
    

     public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Convertir entidad Usuario a UsuarioDTO
    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setCorreo(usuario.getCorreo());
        dto.setNombres(usuario.getNombres());
        dto.setApellido(usuario.getApellido());
        dto.setRol(usuario.getRol());
        return dto;
    }

  


    public Usuario crearUsuario(String username, String password, String nombres , String apellido, String correo, Long roleId ){
        Rol role = roleRepositoy.findById(roleId).orElseThrow(()-> new RuntimeException("Rol no encontrado"));



        Usuario user = new Usuario();

        user.setUsername(username);
        user.setPassword(password);
        user.setCorreo(correo);
        user.setRol(role);
        user.setNombres(nombres);
        user.setApellido(apellido);
        return usuarioRepository.save(user);


        
        
    }

    public void eliminarUsuario (Long userId){

        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(()->new RuntimeException("usuario no encontrado"+userId));

        usuarioRepository.delete(usuario);
    }



}
