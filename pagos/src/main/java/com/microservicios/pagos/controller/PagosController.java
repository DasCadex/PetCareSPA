package com.microservicios.pagos.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.pagos.model.Pagos;
import com.microservicios.pagos.service.PagosService;

@RestController
@RequestMapping ("/api/pagos")

public class PagosController {

    @Autowired
    private PagosService pagosService;

    @GetMapping
    public ResponseEntity <List<Pagos>>  listapagos(){
          List<Pagos> lista2 = pagosService.obtenerPagos();

        if (lista2.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista2);
        
        
    }

    @GetMapping("/{id}")
    public Pagos obetenerPagoPorId(@PathVariable long id){

        return pagosService.obtenerporid(id);
    }

    @PostMapping
    public ResponseEntity<?> crearPago(@RequestBody Pagos nuevoPago) {
    try {
        Pagos pagoGuardado = pagosService.agregarpagos(nuevoPago);
        return ResponseEntity.status(201).body(pagoGuardado);
    } catch (RuntimeException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
}
