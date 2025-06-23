package com.microservicios.mascotas.controller;

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

import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.service.MascotasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping ("/api/mascotas")

public class MascotasController {


    @Autowired
    private MascotasService mascotasservice;



    @Operation(summary = "Obtener las Mascotas")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Mascotas encontradas",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Mascotas.class)))
    })

    @GetMapping
    public List <Mascotas> listaMascotas(){
        return mascotasservice.obatenerMascotas();
    }

    @Operation(summary = "Crear una nueva mascota")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Mascota creada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Mascotas.class))),
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
        content = @Content)
    })
    @PostMapping
    public ResponseEntity <?> crearMascotas(@RequestBody Mascotas mascotas){

        try{
            Mascotas savedMascota = mascotasservice.agregarmascota(mascotas);
            return ResponseEntity.status(201).body(savedMascota);

        }catch(RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
        
    }

    @Operation(summary = "Obtener mascota por ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Mascota encontrada",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Mascotas.class))),
    @ApiResponse(responseCode = "404", description = "Mascota no encontrada",
        content = @Content)
    })
    @GetMapping("/{id}")
    public Mascotas obetenerMascotas(@PathVariable long id){

        return mascotasservice.obtenerporid(id);
    }


    @Operation(summary = "Eliminar mascota por ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Mascota eliminada exitosamente",
        content = @Content),
    @ApiResponse(responseCode = "404", description = "Mascota no encontrada",
        content = @Content)
    })
    @DeleteMapping("/{id}")
    public void eliminarMascotas(@PathVariable long id){
        mascotasservice.eliminarMascotas(id);
    }

    @Operation(summary = "Actualizar una mascota por ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Mascota actualizada exitosamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Mascotas.class))),
    @ApiResponse(responseCode = "400", description = "Mascota no encontrada o datos inválidos",
        content = @Content),
    @ApiResponse(responseCode = "500", description = "Error interno al actualizar la mascota",
        content = @Content)
    })
    @PutMapping("/{id}")

    public ResponseEntity <?>  actualizarMascota(@PathVariable Long id, @RequestBody Mascotas actualizarMascotas ){
        try{

            Mascotas existente = mascotasservice.obtenerporid(id);
            if (existente == null){
                return ResponseEntity.status(400).body("Mascota no encontrada por si ID"+id);


            }


            existente.setEdad(actualizarMascotas.getEdad());
            existente.setEspecie(actualizarMascotas.getEspecie());
            existente.setNombre(actualizarMascotas.getNombre());
            existente.setRaza(actualizarMascotas.getRaza());

            Mascotas actualizado = mascotasservice.agregarmascota(existente);


            return ResponseEntity.ok(actualizado);


        }catch(Exception e){

            return ResponseEntity.status(500).body("Error al hacer el cambio");

        }
    }


    
}
