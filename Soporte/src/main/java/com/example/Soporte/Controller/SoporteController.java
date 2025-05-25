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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
    boolean eliminada = solicitudSoporteService.eliminarSolicitud(id);
    if (eliminada) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


    


}
