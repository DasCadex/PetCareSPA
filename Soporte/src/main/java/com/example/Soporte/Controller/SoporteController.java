package com.example.Soporte.Controller;

import com.example.Soporte.Model.Soporte;
import com.example.Soporte.Service.SoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/soporte") 
public class SoporteController {

    @Autowired
    private SoporteService solicitudSoporteService;

   
    @PostMapping
    public ResponseEntity<Soporte> crearSolicitud(@RequestBody Soporte solicitud) {
        Soporte nuevaSolicitud = solicitudSoporteService.crearSolicitud(solicitud);
        return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
    }

    
    @GetMapping
    public ResponseEntity<List<Soporte>> obtenerTodasLasSolicitudes() {
        List<Soporte> solicitudes = solicitudSoporteService.obtenerTodasLasSolicitudes();
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Soporte> obtenerSolicitudPorId(@PathVariable Long id) {
        return solicitudSoporteService.obtenerSolicitudPorId(id)
                .map(solicitud -> new ResponseEntity<>(solicitud, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

 
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Soporte> actualizarEstadoSolicitud(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Soporte solicitudActualizada = solicitudSoporteService.actualizarEstadoSolicitud(id, nuevoEstado);
            return new ResponseEntity<>(solicitudActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
