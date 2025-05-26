package com.example.Notificaciones.Service;

import com.example.Notificaciones.Model.Notificaciones;
import com.example.Notificaciones.Repository.NotificacionesRepository;
import com.example.Notificaciones.WebClient.PagosClient;
import com.example.Notificaciones.WebClient.UsuarioClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacionesService {

    @Autowired
    private NotificacionesRepository notificacionesRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private PagosClient pagosClient;

  
    

    
    public Notificaciones crearNotificacion(Notificaciones nuevanotificaciones) {

        Map<String, Object> usuario = usuarioClient.getUsuarioById(nuevanotificaciones.getIdusuario());
        if (usuario == null || usuario.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado, no se puede crear la orden de compra");
        }

        Map<String, Object>  pagos = pagosClient.getPagosById(nuevanotificaciones.getIdpago());
          if (pagos== null || pagos.isEmpty()) {
            throw new RuntimeException("pago no encontrado ");
        }


        String nombreadmin= (String ) usuario.get("nombres");
        if (nombreadmin==null) {
            throw new RuntimeException("Nombre no encontrado");
            
        }

        nuevanotificaciones.setNombreAdmin(nombreadmin);

        
  
        return notificacionesRepository.save(nuevanotificaciones);
    }

    
    public List<Notificaciones> obtenerTodasLasNotificaciones() {
        return notificacionesRepository.findAll();
    }

    
    public Optional<Notificaciones> obtenerNotificacionPorId(Long idnotificacion) {
        return notificacionesRepository.findById(idnotificacion);
    }

    

   
    public boolean eliminarNotificacion(Long idnotificacion) {
        if (notificacionesRepository.existsById(idnotificacion)) {
            notificacionesRepository.deleteById(idnotificacion);
            return true;
        }
        return false;
    }
}

