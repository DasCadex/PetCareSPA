package com.example.ordenecompra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordenecompra.model.Orden;
import com.example.ordenecompra.service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/ordenes")

public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @Operation(summary = "obtiene  la lista de todas las ordenes de compra que existan  ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de ordnes de compra mostrada con exito  ", content = @Content(schema = @Schema(implementation = Orden.class)))
    })

    @GetMapping
    public ResponseEntity<List<Orden>> mostrarOrdenes() {
        List<Orden> lista2 = ordenService.buscarOrden();

        if (lista2.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista2);
    }

    @Operation(summary = "nos permite calcular todo lo que ah gatado un usuario ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pagos de usuario mostrado con exito  ", content = @Content(schema = @Schema(implementation = Orden.class)))
    })

    @GetMapping("/total")
    public ResponseEntity<Double> obtenerTotal() {
        Double total = ordenService.calcularTotalOrdenes();
        return ResponseEntity.ok(total);
    }

    @Operation(summary = "Esto nos permite crear una nueva orden de compra ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "orden de compra creada sin error ", content = @Content(schema = @Schema(implementation = Orden.class)))
    })

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody Orden orden) {

        try {
            Orden savedOrden = ordenService.guardarOrdenCompre(orden);
            return ResponseEntity.status(201).body(savedOrden);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary = "Esto nos permite buscar una ordennde compra a traves de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "orden encontrada sin problemas ", content = @Content(schema = @Schema(implementation = Orden.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerOrdenPorId(@PathVariable Long id) {

        try {
            Orden orden = ordenService.buscarPorid(id);
            return ResponseEntity.ok(orden);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Orden>> obtenerOrdenesPorUsuario(@PathVariable Long usuarioId) {
        List<Orden> ordenes = ordenService.buscarPorUsuario(usuarioId);
        return ordenes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ordenes);
    }

    @Operation(summary = "Esto nos permite eliminar una orden o anularla a traves de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "orden petada con exito ", content = @Content(schema = @Schema(implementation = Orden.class)))
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
