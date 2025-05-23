package com.example.HistorialMedico.Controller;

import com.example.HistorialMedicoMascotas.Model.HistorialMedicoModel;
import com.example.HistorialMedicoMascotas.Service.HistorialMedicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/historialmedico")
public class HistorialMedicoController {

    private final HistorialMedicoService historialMedicoService;

   
    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService = historialMedicoService;
    }

   
    @GetMapping
    public ResponseEntity<List<HistorialMedicoModel>> getAllHistorialMedico() {
        List<HistorialMedicoModel> historiales = historialMedicoService.getAllHistorialMedico();
        if (historiales.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Sin Contenido
        }
        return new ResponseEntity<>(historiales, HttpStatus.OK); // 200 OK
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedicoModel> getHistorialMedicoById(@PathVariable Long id) {
        return historialMedicoService.getHistorialMedicoById(id)
                .map(historial -> new ResponseEntity<>(historial, HttpStatus.OK)) // 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 No Encontrado
    }

    
    /*
    {   ej
        "mascotaId": 1,
        "fechaConsulta": "2023-05-15",
        "motivoConsulta": "Dolor de pata",
        "diagnostico": "Esguince leve",
        "tratamiento": "Antiinflamatorios y reposo",
        "vacunas": "Ninguna"
    }
    */
    @PostMapping
    public ResponseEntity<HistorialMedicoModel> createHistorialMedico(@RequestBody HistorialMedicoModel historialMedico) {
        HistorialMedicoModel newHistorial = historialMedicoService.saveHistorialMedico(historialMedico);
        return new ResponseEntity<>(newHistorial, HttpStatus.CREATED); // 201 Creado
    }

   
    /*
    {    ej
        "mascotaId": 1,
        "fechaConsulta": "2023-05-20",
        "motivoConsulta": "Revisión de esguince",
        "diagnostico": "Mejoría significativa",
        "tratamiento": "Continuar reposo por 3 días",
        "vacunas": "Rabia aplicada"
    }
    */
    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedicoModel> updateHistorialMedico(@PathVariable Long id, @RequestBody HistorialMedicoModel historialMedicoDetails) {
        HistorialMedicoModel updatedHistorial = historialMedicoService.updateHistorialMedico(id, historialMedicoDetails);
        if (updatedHistorial != null) {
            return new ResponseEntity<>(updatedHistorial, HttpStatus.OK); // 200 OK
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 No Encontrado
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialMedico(@PathVariable Long id) {
        if (historialMedicoService.deleteHistorialMedico(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Sin Contenido
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 No Encontrado
    }

    
    @GetMapping("/mascota-details/{mascotaId}")
    public Mono<ResponseEntity<HistorialMedicoService.MascotaDto>> getMascotaDetails(@PathVariable Long mascotaId) {
        return historialMedicoService.getMascotaDetailsFromExternalService(mascotaId)
                .map(mascotaDto -> new ResponseEntity<>(mascotaDto, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Devuelve 404 si el servicio externo devuelve vacio o error
    }
}
