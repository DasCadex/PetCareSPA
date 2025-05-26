package com.example.Notificaciones.Service;

import com.example.Notificaciones.Model.Notificaciones;
import com.example.Notificaciones.Repository.NotificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacionesService {

    private final NotificacionesRepository notificacionesRepository;

    @Autowired
    public NotificacionesService(NotificacionesRepository notificacionesRepository) {
        this.notificacionesRepository = notificacionesRepository;
    }

    
    public Notificaciones crearNotificacion(Notificaciones notificaciones) {
        
        if (notificaciones.getIdpago() == null) { 
            throw new IllegalArgumentException("El ID de pago es obligatorio.");
        }
        if (notificaciones.getMensaje() == null || notificaciones.getMensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de la notificación es obligatorio.");
        }
        
        if (notificaciones.getFechaCreacion() == null || notificaciones.getFechaCreacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de creación es obligatoria y debe ser proporcionada.");
        }

        return notificacionesRepository.save(notificaciones);
    }

    
    public List<Notificaciones> obtenerTodasLasNotificaciones() {
        return notificacionesRepository.findAll();
    }

    
    public Optional<Notificaciones> obtenerNotificacionPorId(Long idnotificacion) {
        return notificacionesRepository.findById(idnotificacion);
    }

    
    public List<Notificaciones> obtenerNotificacionesNoLeidas() {
        return notificacionesRepository.findByLeidaFalse();
    }

    
    public List<Notificaciones> obtenerNotificacionesPorIdPago(Long idpago) {
        return notificacionesRepository.findByIdpago(idpago);
    }

    
    public Optional<Notificaciones> marcarComoLeida(Long idnotificacion) {
        return notificacionesRepository.findById(idnotificacion).map(notificacion -> {
            notificacion.setLeida(true);
            return notificacionesRepository.save(notificacion);
        });
    }

   
    public boolean eliminarNotificacion(Long idnotificacion) {
        if (notificacionesRepository.existsById(idnotificacion)) {
            notificacionesRepository.deleteById(idnotificacion);
            return true;
        }
        return false;
    }
}

