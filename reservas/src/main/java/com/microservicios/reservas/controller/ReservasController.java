package com.microservicios.reservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.reservas.model.Reservas;
import com.microservicios.reservas.service.ReservasService;

@RestController
@RequestMapping("/api/reservas")
public class ReservasController {

    @Autowired
    private ReservasService reservasService;

    @GetMapping //
    public List<Reservas> listareservas(){
        return reservasService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Reservas obetenerPorId(@PathVariable Long id){
        return reservasService.obtenerporid(id);

    }


    @PostMapping
    public ResponseEntity <?> crearmascota (@RequestBody Reservas reservas){

        try{
            Reservas savedReservas = reservasService.crearReserva(reservas);
            return ResponseEntity.status(201).body(savedReservas);

        }catch(RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }


    
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){
        reservasService.eliminar(id);
        // Elimina la reserva correspondiente al id indicado
    }
    
}
