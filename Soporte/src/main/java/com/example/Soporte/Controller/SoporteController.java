package com.example.Soporte.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Soporte.Model.Soporte;
import com.example.Soporte.Service.SoporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/soporte")
public class SoporteController {

    @Autowired
    private SoporteService solicitudSoporteService;


    
    @Operation(summary= "Obtener las Solicitudes")
    @ApiResponses(value = {
    @ApiResponse(responseCode= "200", description="Solicitudes encontradas",
    content= @Content(mediaType= "application/json",
    schema= @Schema(implementation = Soporte.class)))
    })
    
    
    @PostMapping
    public ResponseEntity<Soporte> crearSolicitud(@RequestBody Soporte solicitud) {
        Soporte nuevaSolicitud = solicitudSoporteService.crearSolicitud(solicitud);
        return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
    }


    @Operation(summary = "Obtener todas las solicitudes de soporte")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Solicitudes encontradas",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class)))
    })
    @GetMapping
    public ResponseEntity<List<Soporte>> obtenerTodasLasSolicitudes() {
        List<Soporte> solicitudes = solicitudSoporteService.obtenerTodasLasSolicitudes();
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }


    @Operation(summary = "Obtener solicitud de soporte por ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Solicitud encontrada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class))),
    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada",
        content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Soporte> obtenerSolicitudPorId(@PathVariable Long id) {
        return solicitudSoporteService.obtenerSolicitudPorId(id)
                .map(solicitud -> new ResponseEntity<>(solicitud, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @Operation(summary = "Eliminar solicitud de soporte por ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Solicitud eliminada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        boolean eliminada = solicitudSoporteService.eliminarSolicitud(id);
        if (eliminada) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Actualizar una solicitud de soporte por ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Solicitud actualizada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class))),
    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada",
        content = @Content),
    @ApiResponse(responseCode = "500", description = "Error interno al actualizar la solicitud",
        content = @Content)
    })
    @PutMapping("/{id}")

    public ResponseEntity<?> actualizarSoporte(@PathVariable Long id, @RequestBody Soporte actualizarSoporte) {
        try {
            Soporte existente = solicitudSoporteService.obtenerSolicitudPorId(id).orElse(null);

            if (existente == null) {
                return ResponseEntity.status(404).body("Solicitud no encontrada para nodificar " + id);
            }

            existente.setFechaCreacion(actualizarSoporte.getFechaCreacion());
            existente.setEstado(actualizarSoporte.getEstado());
            existente.setTipoSolicitud(actualizarSoporte.getTipoSolicitud());
            existente.setMensaje(actualizarSoporte.getMensaje());

            Soporte actualizado = solicitudSoporteService.crearSolicitud(existente);

            return ResponseEntity.ok(actualizado);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar solicitud");

        }
    }

    @Operation(summary = "Obtener solicitudes por ID de usuario")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Solicitudes encontradas",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Soporte.class))),
    @ApiResponse(responseCode = "204", description = "No se encontraron solicitudes")
    })
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Soporte>> obtenerSolicitudesPorUsuario(@PathVariable Long usuarioId) {
        List<Soporte> solicitudes = solicitudSoporteService.obtenerSolicitudesPorUsuario(usuarioId);
        if (solicitudes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }

}
