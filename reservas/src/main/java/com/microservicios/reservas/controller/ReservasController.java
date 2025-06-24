package com.microservicios.reservas.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.reservas.model.Reservas;
import com.microservicios.reservas.service.ReservasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/reservas")
public class ReservasController {

    @Autowired
    private ReservasService reservasService;


    @Operation(summary = "obtiene  la lista de todas las reservas que existen en la bd ")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Listas de reservas encontrada con exito ",
        content = @Content(schema = @Schema(implementation = Reservas.class)))
    })
    @GetMapping //
    public List<Reservas> listareservas() {
        return reservasService.obtenerTodas();
    } 
    
    @Operation(summary = "obtiene  la lista de todas las reservas que existan a treaves de la id")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Reserva especifica buscada por id  exitoso ",
        content = @Content(schema = @Schema(implementation = Reservas.class)))
    })

    @GetMapping("/{id}")
    public Reservas obetenerPorId(@PathVariable Long id) {
        return reservasService.obtenerporid(id);

    }

    @Operation(summary = "Nos permite crear reservas para crear una nueva reserva")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Reserva creada con exito ",
        content = @Content(schema = @Schema(implementation = Reservas.class)))
    })

    @PostMapping
    public ResponseEntity<?> crearmascota(@RequestBody Reservas reservas) {

        try {
            Reservas savedReservas = reservasService.crearReserva(reservas);
            return ResponseEntity.status(201).body(savedReservas);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    @Operation(summary = "Nos permite eliminar alguna reserva a traves de su id")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "reserva eliminada con exito ",
        content = @Content(schema = @Schema(implementation = Reservas.class)))
    })

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id) {
        reservasService.eliminar(id);
        // Elimina la reserva correspondiente al id indicado
    }



    
    @Operation(summary = "Nos permite editar alguna reserva a traves de su id por si existe algun error")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "reserva esitda copn exito",
        content = @Content(schema = @Schema(implementation = Reservas.class)))
    })



    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable Long id, @RequestBody Reservas reservaActualizada) {
    try {
        Reservas existente = reservasService.obtenerporid(id);
        if (existente == null) {
            return ResponseEntity.status(404).body("Reserva no encontrada con ID: " + id);
        }

        // Lógica para actualizar campos (menos el nombre del cliente y precio si quieres que sea fijo)
        existente.setFecha(reservaActualizada.getFecha());
        existente.setMotivo(reservaActualizada.getMotivo());
        existente.setObservaciones(reservaActualizada.getObservaciones());


        Reservas actualizada = reservasService.crearReserva(existente); // reutiliza lógica del POST

        return ResponseEntity.ok(actualizada);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error al actualizar la reserva: " + e.getMessage());
    }
    
}


}
