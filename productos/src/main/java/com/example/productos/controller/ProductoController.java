package com.example.productos.controller;

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

import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@Validated

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtiene  la lista de todos los productos")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200",description = "Listas de Productos encontrada con exito",
        content = @Content(schema = @Schema(implementation = Producto.class)))
    })



    @GetMapping

    public ResponseEntity <List <Producto>> listaProducto(){

        List <Producto> lista2=productoService.buscarProducto();

        if(lista2.isEmpty()){
            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.ok(lista2);


        
    }

    @PostMapping

    public ResponseEntity <Producto> guardarProducto (@Valid @RequestBody Producto producto){

        Producto pac= productoService.agregarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pac);
    }

     
    @GetMapping("/{id}")
    public ResponseEntity <?> buscarPorId(@PathVariable Long id ){

        try{
            Producto producto =productoService.buscarProductoPorId(id);
            return ResponseEntity.ok(producto);



        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> borrarPacientePorId(@PathVariable Long id ){

        try{

            Producto pro= productoService.buscarProductoPorId(id);
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();

        }catch(Exception e ){

            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProductoPorId(@PathVariable Long id, @RequestBody Producto pro) {
    try {
        // Buscamos si existe
        Producto producto2 = productoService.buscarProductoPorId(id);

        // Modificamos sus atributos
        producto2.setId(id); // Ahora sí coincide el tipo
        producto2.setMarca_producto(pro.getMarca_producto());
        producto2.setNombre_producto(pro.getNombre_producto());
        producto2.setPrecio_producto(pro.getPrecio_producto());
        producto2.setStock(pro.getStock());

        // Guardamos en la BD
        productoService.agregarProducto(producto2);
        return ResponseEntity.ok(producto2);

    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}
  
}
