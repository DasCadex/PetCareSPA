package com.example.HistorialMedico.Controller;

import java.util.List;
import java.util.Optional;

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

import com.example.HistorialMedico.Model.HistorialMedico;
import com.example.HistorialMedico.Service.HistorialMedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/historialmedico")
public class HistorialMedicoController {

    private final HistorialMedicoService historialMedicoService;

    
    @Autowired
    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
    }

    @Operation(summary= "Obtener los historiales medicos")
    @ApiResponses(value = {
    @ApiResponse(responseCode= "200", description="Historiales encontrados",
    content= @Content(mediaType= "application/json",
    schema= @Schema(implementation = HistorialMedico.class)))
    })

    @PostMapping
    public ResponseEntity<?> crearHistorialMedico(@RequestBody HistorialMedico historial) {
        try{
             HistorialMedico savedHistorial = historialMedicoService.crearHistorialMedico(historial);
            return ResponseEntity.status(201).body(savedHistorial);

        }catch(RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
        
  

        }

        @GetMapping
        public ResponseEntity<List<HistorialMedico>> obtenerTodosLosHistoriales() {
            List<HistorialMedico> historiales = historialMedicoService.obtenerTodosLosHistoriales();
            return new ResponseEntity<>(historiales, HttpStatus.OK);
        }

        @GetMapping("/{idhistorial}")
        public ResponseEntity<HistorialMedico> obtenerHistorialMedicoPorId(@PathVariable Long idhistorial) {
            Optional<HistorialMedico> historial = historialMedicoService.obtenerHistorialMedicoPorId(idhistorial);
            return historial.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }


        

        @DeleteMapping("/{idhistorial}") // <-- Ajustado la URL para el ID del historial
        public ResponseEntity<Void> eliminarHistorialMedico(@PathVariable Long idhistorial) {
            if (historialMedicoService.eliminarHistorialMedico(idhistorial)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PutMapping("/{id}")

        public  ResponseEntity <?> actualizarHistorial (@PathVariable Long id, @RequestBody  HistorialMedico actualizarHistorial){
            try{
                HistorialMedico existente = historialMedicoService.obtenerHistorialMedicoPorId(id).orElseThrow();
                
                if(existente == null){
                    return ResponseEntity.status(400).body("Historial no encontrado o inexistente"+id);

                }

                existente.setDiagnostico(actualizarHistorial.getDiagnostico());
                existente.setTratamiento(actualizarHistorial.getTratamiento());
                existente.setFechaconsulta(actualizarHistorial.getFechaconsulta());
                
                HistorialMedico actualizado = historialMedicoService.crearHistorialMedico(existente);

                return ResponseEntity.ok(actualizado);

            }catch(Exception e){
                return ResponseEntity.status(500).body("Error al modificar el Historial medico de la mascota ");
            }

        }

           @GetMapping("/usuario/{usuarioId}")
        public ResponseEntity<List<HistorialMedico>> obtenerSolicitudesPorUsuario(@PathVariable Long usuarioId) {
        List<HistorialMedico> solicitudes = historialMedicoService.ObtenerSolicitudesPorUsuario(usuarioId);
        if (solicitudes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }






}
    

    





