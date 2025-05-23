package com.example.notificaciones.Controller;

import com.example.notificaciones.Model.Notificacion;
import com.example.notificaciones.Service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones") 
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

   
    //   "tipo": "Cita Proxima",
    //   "destinatario": "paciente@clinic.com",
    //   "mensaje": "Tienes una cita el 25/05/2025 a las 10:00 AM.",
    //   "referenciaExternaId": "cita_456"
    // }
    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion,
                                                          @RequestParam(value = "enviar", defaultValue = "false") boolean enviarInmediatamente) {
        try {
            Notificacion nuevaNotificacion = notificacionService.crearNotificacion(notificacion, enviarInmediatamente);
            return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // O un HttpStatus.BAD_REQUEST si la validación falla
        }
    }

   
    
    @PostMapping("/{id}/enviar")
    public ResponseEntity<Notificacion> enviarNotificacion(@PathVariable Long id) {
        try {
            Notificacion notificacionEnviada = notificacionService.enviarNotificacion(id);
            return new ResponseEntity<>(notificacionEnviada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodasLasNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.obtenerTodasLasNotificaciones();
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerNotificacionPorId(@PathVariable Long id) {
        return notificacionService.obtenerNotificacionPorId(id)
                .map(notificacion -> new ResponseEntity<>(notificacion, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        try {
            notificacionService.eliminarNotificacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content para eliminación exitosa
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
 
