package com.microservicios.promociones_ofertas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.promociones_ofertas.model.promocion;
import com.microservicios.promociones_ofertas.service.PromocionService;



@RestController
@RequestMapping("/api/promociones")
@Validated

public class promocionControlador {

    @Autowired
    private PromocionService promocionService;

    @GetMapping

    public ResponseEntity <List <promocion>> listaProducto(){

        List <promocion> lista2=promocionService.buscartodasPromociones();

        if(lista2.isEmpty()){
            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.ok(lista2);
    }

    @PostMapping
    
    public ResponseEntity <promocion> guardarPromocion (@Validated @RequestBody promocion promocion){

        promocion prom= promocionService.agregarPromocion(promocion);
        return ResponseEntity.status(HttpStatus.CREATED).body(prom);

    } 

    @GetMapping ("/{id}")
    public ResponseEntity <?> buscarPromocionporid(@PathVariable long id){

        try {
            
            promocion promocion = promocionService.buscarPromocionporid(id);
            
            return ResponseEntity.ok(promocion);
        } catch (Exception e) {

            return ResponseEntity.notFound().build();
        }

    }



}
