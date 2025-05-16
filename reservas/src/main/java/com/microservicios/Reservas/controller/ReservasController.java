package com.microservicios.Reservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.Reservas.model.Reservas;
import com.microservicios.Reservas.service.ReservasService;

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

    @GetMapping("/estado/{estado}")
   public List<Reservas> obtenerPorEstado(@PathVariable String estado) {
        return reservasService.obtenerPorEstado(estado);
    }

    @PostMapping
    public Reservas crear(@RequestBody Reservas reservas){

        return reservasService.crearReserva(reservas);
        // Maneja solicitudes HTTP POST a la ruta base (por ejemplo, /reservas)
        // Crea una nueva reserva utilizando los datos recibidos en el cuerpo de la solicitud (en formato JSON)
        // Retorna la reserva creada
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id){
        reservasService.eliminar(id);
        // Elimina la reserva correspondiente al id indicado
    }
    
}
