package com.example.Soporte.controller;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Soporte.Controller.SoporteController;
import com.example.Soporte.Model.Soporte;
import com.example.Soporte.Service.SoporteService;
import com.fasterxml.jackson.databind.ObjectMapper;

    

@WebMvcTest(SoporteController.class)
public class SoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoporteService soporteService;


    @Test
    void getAllSolicitudes_retornaListaYStatus200() throws Exception {
        List<Soporte> lista = List.of(new Soporte(1L, "usuario", "correo", "mensaje", "fecha", "estado", "tipo", 1L));
        when(soporteService.obtenerTodasLasSolicitudes()).thenReturn(lista);

        mockMvc.perform(get("/api/soporte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreusuario").value("usuario"));
    }

    @Test
    void getSolicitudPorId_retornaSolicitudYStatus200() throws Exception {
        Soporte soporte = new Soporte(1L, "usuario", "correo", "mensaje", "fecha", "estado", "tipo", 1L);
        when(soporteService.obtenerSolicitudPorId(1L)).thenReturn(Optional.of(soporte));

        mockMvc.perform(get("/api/soporte/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("mensaje"));
    }

    
    @Test
    void postSolicitud_creaSoporteYDevuelve201() throws Exception {
        Soporte soporte = new Soporte(null, "usuario", "correo", "mensaje", "fecha", "estado", "tipo", 1L);
        when(soporteService.crearSolicitud(any())).thenReturn(soporte);

        mockMvc.perform(post("/api/soporte")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(soporte)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje").value("mensaje"));
    }
    
    @Test
    void deleteSolicitud_existente_retorna204() throws Exception {
        when(soporteService.eliminarSolicitud(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/soporte/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void getSolicitudesPorUsuarioId_retornaListaYStatus200() throws Exception {
        List<Soporte> lista = List.of(new Soporte(1L, "usuario", "correo", "mensaje", "fecha", "estado", "tipo", 1L));
        when(soporteService.obtenerSolicitudesPorUsuario(1L)).thenReturn(lista);

        mockMvc.perform(get("/api/soporte/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(1L));
    }
}
