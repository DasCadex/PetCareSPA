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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping ("/api/pagos")

public class PagosController {

    @Autowired
    private PagosService pagosService;

      @Operation(summary = "Obtiene la lista de todos los pagos")
      @ApiResponses(value ={
      @ApiResponse(responseCode = "200",description = "Listas de Pagos encontrada con exito",
      content = @Content(schema = @Schema(implementation = Pagos.class)))
    })


    @GetMapping
    public ResponseEntity <List<Pagos>>  listapagos(){
          List<Pagos> lista2 = pagosService.obtenerPagos();

        if (lista2.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista2);
        
        
    }

    @Operation(summary = "Obtiene la lista de todos los pagos por su id")
    @ApiResponses(value ={
    @ApiResponse(responseCode = "200",description = "Listas de Pagos por id encontrada con exito",
    content = @Content(schema = @Schema(implementation = Pagos.class)))
  })

    @GetMapping("/{id}")
    public Pagos obetenerPagoPorId(@PathVariable long id){

        return pagosService.obtenerporid(id);
    }

    @Operation(summary = "Metodo que permite Crea Pagos")
    @ApiResponses(value ={
    @ApiResponse(responseCode = "201",description = "Pago Creado Con Exito",
    content = @Content(schema = @Schema(implementation = Pagos.class)))
  })

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
