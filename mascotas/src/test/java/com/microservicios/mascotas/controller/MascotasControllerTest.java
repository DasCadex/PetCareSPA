package com.microservicios.mascotas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.service.MascotasService;

@WebMvcTest(MascotasController.class)
public class MascotasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotasService mascotasService;

    @Test
    void getAllMascotas_retornaListaYStatus200() throws Exception {
        Mascotas mascota = new Mascotas(2L, 1L, "ELPEPE", 3, "Pug", "Perro", "Carlos");
        List<Mascotas> lista = List.of(mascota);

        when(mascotasService.obatenerMascotas()).thenReturn(lista);

        mockMvc.perform(get("/api/mascotas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("ELPEPE"));
    }

    @Test
    void getMascotaById_retornaMascotaYStatus200() throws Exception {
        Mascotas mascota = new Mascotas(1L, 1L, "Nina", 2, "Persa", "Gato", "María");

        when(mascotasService.obtenerporid(1L)).thenReturn(mascota);

        mockMvc.perform(get("/api/mascotas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nina"));
    }

    @Test
    void createMascota_retorna201CuandoExito() throws Exception {
        Mascotas mascota = new Mascotas(1L, 1L, "Luna", 1, "Chihuahua", "Perro", "Ana");

        when(mascotasService.agregarmascota(any())).thenReturn(mascota);

        mockMvc.perform(post("/api/mascotas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mascota)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Luna"));
    }

    @Test
    void updateMascota_retornaMascotaActualizadaYStatus200() throws Exception {
        Mascotas mascotaOriginal = new Mascotas(1L, 1L, "Arros con webo", 3, "Pug", "Perro", "Carlos");
        Mascotas mascotaActualizada = new Mascotas(1L, 1L, "dawdawd", 4, "Bulldog", "Perro", "Carlos");

        when(mascotasService.obtenerporid(1L)).thenReturn(mascotaOriginal);
        when(mascotasService.agregarmascota(any())).thenReturn(mascotaActualizada);

        mockMvc.perform(put("/api/mascotas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mascotaActualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.edad").value(4))
                .andExpect(jsonPath("$.raza").value("Bulldog"));
    }

    @Test
    void updateMascota_idNoExiste_retorna400() throws Exception {
        Mascotas mascota = new Mascotas(1L, 1L, "Toby", 2, "Labrador", "Perro", "Juan");

        when(mascotasService.obtenerporid(99L)).thenReturn(null);

        mockMvc.perform(put("/api/mascotas/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mascota)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Mascota no encontrada por si ID99"));
    }

    @Test
    void deleteMascota_eliminaCorrectamenteYRetorna200() throws Exception {
        doNothing().when(mascotasService).eliminarMascotas(1L);

        mockMvc.perform(delete("/api/mascotas/1"))
                .andExpect(status().isOk());
    }
}