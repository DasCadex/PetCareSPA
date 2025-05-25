package com.example.HistorialMedico.Controller;

import com.example.HistorialMedico.Model.HistorialMedico;
import com.example.HistorialMedico.Service.HistorialMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historialmedico")
public class HistorialMedicoController {

    private final HistorialMedicoService historialMedicoService;

    @Autowired
    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
    }

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





}
    

    





