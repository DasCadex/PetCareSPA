package com.example.Notificaciones.Controller;

import com.example.Notificaciones.Model.Notificaciones; 
import com.example.Notificaciones.Service.NotificacionesService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificaciones") 
public class NotificacionesController {


    @Autowired
    private NotificacionesService notificacionesService; 


    
    @PostMapping
    public ResponseEntity<?> crearNotificacion(@RequestBody Notificaciones notificacion) { 
        try {
            Notificaciones nuevaNotificacion = notificacionesService.crearNotificacion(notificacion); 
            return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    
    @GetMapping
    public ResponseEntity<List<Notificaciones>> obtenerTodasLasNotificaciones() { 
        List<Notificaciones> notificaciones = notificacionesService.obtenerTodasLasNotificaciones(); 
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

   
    @GetMapping("/{idnotificacion}") 
    public ResponseEntity<Notificaciones> obtenerNotificacionPorId(@PathVariable Long idnotificacion) { 
        Optional<Notificaciones> notificacion = notificacionesService.obtenerNotificacionPorId(idnotificacion); 
        return notificacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    
    @DeleteMapping("/{idnotificacion}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long idnotificacion) {
        if (notificacionesService.eliminarNotificacion(idnotificacion)) { 
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


