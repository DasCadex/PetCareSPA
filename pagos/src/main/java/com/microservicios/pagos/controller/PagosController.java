package com.microservicios.pagos.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.pagos.model.Pagos;
import com.microservicios.pagos.service.PagosService;

@RestController
@RequestMapping ("/api/mascotas")

public class PagosController {

    @Autowired
    private PagosService pagosService;

    @GetMapping
    public List <Pagos> listapagos(){
        return pagosService.obtenerPagos();
        
    }

    @GetMapping("/{id}")
    public Pagos obetenerMascotas(@PathVariable long id){

        return pagosService.obtenerporid(id);
    }
}
