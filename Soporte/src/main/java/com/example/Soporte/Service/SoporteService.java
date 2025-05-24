package com.example.Soporte.Service;

import com.example.Soporte.Model.Soporte;
import com.example.Soporte.Repository.SoporteRepository;
import com.example.Soporte.WebClient.UsuarioClient;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional 
public class SoporteService {

    @Autowired
    private SoporteRepository solicitudSoporteRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    // Método para crear una nueva solicitud de soporte (POST)
    public Soporte crearSolicitud(Soporte nuevasolicitud) {
    System.out.println("Buscando usuario con ID: " + nuevasolicitud.getUsuarioId());

    Map<String, Object> usuario = usuarioClient.getUsuarioById(nuevasolicitud.getUsuarioId());

    if (usuario == null || usuario.isEmpty()) {
        throw new RuntimeException("Usuario soporte no existe");
    }

    System.out.println("Usuario recibido: " + usuario);

    String nombreusuario = (String) usuario.get("username");
    if (nombreusuario == null) {
        throw new RuntimeException("No existe un soporte con este nombre");
    }

    String correousuario = (String) usuario.get("correo");
    if (correousuario == null) {
        throw new RuntimeException("Correo del soporte inválido o inexistente");
    }

    Map<String, Object> rolMap = (Map<String, Object>) usuario.get("rol");
    if (rolMap == null || !rolMap.get("nombre").toString().equalsIgnoreCase("Soporte y administrador de sistemas")) {
    throw new RuntimeException("Acceso denegado: el usuario no tiene el rol SOPORTE");
    }


   

    nuevasolicitud.setNombreusuario(nombreusuario);
    nuevasolicitud.setEmailusuario(correousuario);

    return solicitudSoporteRepository.save(nuevasolicitud);
}


    // Método para obtener todas las solicitudes de soporte (GET)
    public List<Soporte> obtenerTodasLasSolicitudes() {
        return solicitudSoporteRepository.findAll();
    }

    // Método para obtener una solicitud por ID (GET)
    public Optional<Soporte> obtenerSolicitudPorId(Long id) {
        return solicitudSoporteRepository.findById(id);
    }

    
 
}
