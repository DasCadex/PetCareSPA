package com.example.ordenecompra.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordenecompra.model.Orden;
import com.example.ordenecompra.service.OrdenService;

@RestController
@RequestMapping("/api/v1/ordenes")

public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<Orden>> mostrarOrdenes() {
    List<Orden> lista2 = ordenService.buscarOrden();

        if (lista2.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista2);
    }
    @GetMapping("/total")
    public ResponseEntity<Double> obtenerTotal() {
    Double total = ordenService.calcularTotalOrdenes();
    return ResponseEntity.ok(total);
}

    @PostMapping
    public ResponseEntity <?>  crearPedido(@RequestBody Orden orden){

        try{
            Orden savedOrden = ordenService.guardarOrdenCompre(orden);
            return ResponseEntity.status(201).body(savedOrden);

        }catch(RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    

    

    

 





}
