package com.example.creacionusuario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.creacionusuario.dto.LoginRequest;
import com.example.creacionusuario.dto.LoginResponse;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public Usuario crearUsuario(String username, String password, String nombres, String apellido, String correo,
            Long roleId) {
        if (password == null || password.isBlank()) {
            throw new RuntimeException("La contraseña no puede ser nula ni vacía");
        }

        Rol role = roleRepositoy.findById(roleId).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario user = new Usuario();
        user.setPassword(passwordEncoder.encode(password));

        user.setUsername(username);
        user.setCorreo(correo);
        user.setRol(role);
        user.setNombres(nombres);
        user.setApellido(apellido);

        return usuarioRepository.save(user);
    }

    public void eliminarUsuario(Long userId) {

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado" + userId));

        usuarioRepository.delete(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setUsername(usuarioActualizado.getUsername());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setNombres(usuarioActualizado.getNombres());
        usuarioExistente.setApellido(usuarioActualizado.getApellido());
        usuarioExistente.setRol(usuarioActualizado.getRol());

        // Solo actualiza la contraseña si viene una nueva
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isBlank()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public LoginResponse autenticarUsuario(String username, String password) {
        Usuario usuario = usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        LoginResponse response = new LoginResponse();
        response.setId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setCorreo(usuario.getCorreo());
        response.setNombres(usuario.getNombres());
        response.setApellido(usuario.getApellido());
        response.setRol(usuario.getRol());

        return response;
    }

}
