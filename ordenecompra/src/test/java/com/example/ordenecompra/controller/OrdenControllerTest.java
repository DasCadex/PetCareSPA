package com.example.ordenecompra.controller;

import com.example.ordenecompra.model.Orden;
import com.example.ordenecompra.service.OrdenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenController.class)
public class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService ordenService;

    @Test
    void getAllOrdenes_shouldReturnOkAndJson() throws Exception {
        Orden orden = new Orden(1L, 2L, 3L, "producto", "cliente", 1500.0, 2, 10L, "promoción");
        List<Orden> lista = Arrays.asList(orden);

        when(ordenService.buscarOrden()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/ordenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_orden").value(1L));
    }

    @Test
    void getOrdenPorId_shouldReturnOk() throws Exception {
        Orden orden = new Orden(1L, 2L, 3L, "producto", "cliente", 1500.0, 2, 10L, "promoción");

        when(ordenService.buscarPorid(1L)).thenReturn(orden);

        mockMvc.perform(get("/api/v1/ordenes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_orden").value(1L));
    }

    @Test
    void eliminarOrden_shouldReturnOk() throws Exception {
        doNothing().when(ordenService).eliminarOrden(1L);

        mockMvc.perform(delete("/api/v1/ordenes/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void crearOrden_shouldReturnCreated() throws Exception {
        Orden orden = new Orden(1L, 2L, 3L, "producto", "cliente", 1500.0, 2, 10L, "promo");
        when(ordenService.guardarOrdenCompre(orden)).thenReturn(orden);

        mockMvc.perform(post("/api/v1/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "usuarioId": 2,
                          "productoId": 3,
                          "nombreProducto": "producto",
                          "nombres": "cliente",
                          "precioProducto": 1500.0,
                          "cantidad": 2,
                          "promocionid": 10,
                          "promocionif": "promo"
                        }
                        """))
                .andExpect(status().isCreated());
    }

    @Test
    void getOrdenesByUsuario_shouldReturnOk() throws Exception {
        Orden orden = new Orden(1L, 5L, 3L, "producto", "cliente", 1500.0, 2, 10L, "promo");
        when(ordenService.buscarPorUsuario(5L)).thenReturn(List.of(orden));

        mockMvc.perform(get("/api/v1/ordenes/usuario/{usuarioId}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(5L));
    }
}
