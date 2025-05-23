package com.example.notificaciones.Service;

import com.example.notificaciones.Model.Notificacion;
import com.example.notificaciones.Repository.NotificacionRepository;
import com.example.notificaciones.WebClient.NotificacionExternaClient; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private NotificacionExternaClient notificacionExternaClient; 

    /**
     
     
      @param notificacion 
      @param enviarInmediatamente 
      @return 
     */

    public Notificacion crearNotificacion(Notificacion notificacion, boolean enviarInmediatamente) {
        // si no viene se pone automatico una fecha
        if (notificacion.getFechaCreacion() == null) {
            notificacion.setFechaCreacion(LocalDateTime.now());
        }
        // estado inicial
        if (notificacion.getEstadoEnvio() == null || notificacion.getEstadoEnvio().isEmpty()) {
            notificacion.setEstadoEnvio("PENDIENTE");
        }

        Notificacion savedNotificacion = notificacionRepository.save(notificacion);

        if (enviarInmediatamente) {
            enviarNotificacion(savedNotificacion.getId()); 
        }

        return savedNotificacion;
    }

    /**      
      @param id 
      @return 
     */

    public Notificacion enviarNotificacion(Long id) {
        return notificacionRepository.findById(id)
                .map(notificacion -> {
                    // Solo enviar si el estado no es ya ENVIADA
                    if (!"ENVIADA".equalsIgnoreCase(notificacion.getEstadoEnvio())) {
                        boolean exitoEnvio = notificacionExternaClient.enviarNotificacionExterna(
                                notificacion.getTipo(),
                                notificacion.getDestinatario(),
                                notificacion.getMensaje(),
                                notificacion.getReferenciaExternaId()
                        );

                        if (exitoEnvio) {
                            notificacion.setEstadoEnvio("ENVIADA");
                            notificacion.setFechaEnvio(LocalDateTime.now());
                        } else {
                            notificacion.setEstadoEnvio("FALLIDA");
                            // Logs adicionales

                        }
                        return notificacionRepository.save(notificacion); 
                    }
                    return notificacion; // Si ya estaba enviada no hacer nada
                })
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
    }

    
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }

    
    public Optional<Notificacion> obtenerNotificacionPorId(Long id) {
        return notificacionRepository.findById(id);
    }

    
    public void eliminarNotificacion(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada para eliminar con ID: " + id);
        }
        notificacionRepository.deleteById(id);
    }
}
   
