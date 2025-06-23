package com.microservicios.mascotas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.service.MascotasService;

@RestController
@RequestMapping ("/api/mascotas")

public class MascotasController {


    @Autowired
    private MascotasService mascotasservice;


    @GetMapping
    public List <Mascotas> listaMascotas(){
        return mascotasservice.obatenerMascotas();
    }

    @PostMapping
    public ResponseEntity <?> crearMascotas(@RequestBody Mascotas mascotas){

        try{
            Mascotas savedMascota = mascotasservice.agregarmascota(mascotas);
            return ResponseEntity.status(201).body(savedMascota);

        }catch(RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
        
    }

    @GetMapping("/{id}")
    public Mascotas obetenerMascotas(@PathVariable long id){

        return mascotasservice.obtenerporid(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarMascotas(@PathVariable long id){
        mascotasservice.eliminarMascotas(id);
    }

    // Endpoint para agrupar mascotas por usuarioId
    @GetMapping("/agrupadas")
    public Map<Long, List<Mascotas>> obtenerMascotasAgrupadasPorUsuario() {
        return mascotasservice.agruparPorUsuario();
    }
    
}
