package com.example.Soporte.Service;

import com.example.Soporte.Model.Soporte;
import com.example.Soporte.Repository.SoporteRepository;
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

    // Método para crear una nueva solicitud de soporte (POST)
    public Soporte crearSolicitud(Soporte solicitud) {
        // La fecha de creacion y el estado (Pendiente) se meten en el constructor
        Soporte savedSolicitud = solicitudSoporteRepository.save(solicitud);    
        return savedSolicitud;
    }

    // Método para obtener todas las solicitudes de soporte (GET)
    public List<Soporte> obtenerTodasLasSolicitudes() {
        return solicitudSoporteRepository.findAll();
    }

    // Método para obtener una solicitud por ID (GET)
    public Optional<Soporte> obtenerSolicitudPorId(Long id) {
        return solicitudSoporteRepository.findById(id);
    }

    // Método para actualizar el estado de una solicitud (PUT)
    public Soporte actualizarEstadoSolicitud(Long id, String nuevoEstado) {
        return solicitudSoporteRepository.findById(id)
                .map(solicitudExistente -> {
                    solicitudExistente.setEstado(nuevoEstado);
                    return solicitudSoporteRepository.save(solicitudExistente);
                })
                .orElseThrow(() -> new RuntimeException("Solicitud de soporte no encontrada con ID: " + id));
    }

 
}
