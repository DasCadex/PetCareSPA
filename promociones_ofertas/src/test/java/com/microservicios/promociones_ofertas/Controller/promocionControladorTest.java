package com.microservicios.promociones_ofertas.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.promociones_ofertas.controller.promocionControlador;
import com.microservicios.promociones_ofertas.model.promocion;
import com.microservicios.promociones_ofertas.service.PromocionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException; 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(promocionControlador.class) 
public class promocionControladorTest {

    @Autowired
    private MockMvc mockMvc; 

    @Autowired
    private ObjectMapper objectMapper; 

    @MockBean 
    private PromocionService promocionService;



    @Test
    void listaProducto_ListaDePromociones() throws Exception {
        
        promocion promo1 = new promocion(1L, "Verano", "Descuento de verano");
        promocion promo2 = new promocion(2L, "Invierno", "Oferta de invierno");
        List<promocion> promociones = Arrays.asList(promo1, promo2);

        when(promocionService.buscartodasPromociones()).thenReturn(promociones);

        
        mockMvc.perform(get("/api/promociones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera un status 200 OK
                .andExpect(jsonPath("$.length()").value(2)) 
                .andExpect(jsonPath("$[0].titulo").value("Verano"));
    }

    @Test
    void listaProducto_NoContentSiListaVacia() throws Exception {
       
        when(promocionService.buscartodasPromociones()).thenReturn(Collections.emptyList());

        
        mockMvc.perform(get("/api/promociones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Espera un status 204 No Content
    }



    @Test
    void guardarPromocion_CrearNuevaPromocion() throws Exception {
        
        promocion nuevaPromo = new promocion(null, "Primavera", "Comida y Sachets");
        promocion promoGuardada = new promocion(3L, "Primavera", "Comida y Sachets");

        when(promocionService.agregarPromocion(any(promocion.class))).thenReturn(promoGuardada);

        
        mockMvc.perform(post("/api/promociones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaPromo))) 
                .andExpect(status().isCreated()) // Espera un status 201 Created
                .andExpect(jsonPath("$.idpromocion").value(3L))
                .andExpect(jsonPath("$.titulo").value("Primavera"));
    }



    @Test
    void buscarPromocionporid_PromocionExistente() throws Exception {
        
        Long promoId = 1L;
        promocion promoExistente = new promocion(promoId, "Black Friday", "Grandes ofertas en Jugetes");

        when(promocionService.buscarPromocionporid(promoId)).thenReturn(promoExistente);

        
        mockMvc.perform(get("/api/promociones/{id}", promoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera un status 200 OK
                .andExpect(jsonPath("$.idpromocion").value(promoId))
                .andExpect(jsonPath("$.titulo").value("Black Friday"));
    }

    @Test
    void buscarPromocionporid_NotFoundSiNoExiste() throws Exception {
        
        Long promoId = 99L; // ID no existente
        when(promocionService.buscarPromocionporid(promoId)).thenThrow(new NoSuchElementException()); // Simula no encontrado

       
        mockMvc.perform(get("/api/promociones/{id}", promoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Espera un status 404 Not Found
    }



    @Test
    void actualizarPagosPorId_ActualizarPromocionExistente() throws Exception {
        
        Long promoId = 1L;
        promocion promoOriginal = new promocion(promoId, "Promo Vieja", "Descripcion Vieja");
        promocion promoActualizadaInput = new promocion(promoId, "Promo Nueva", "Descripcion Nueva");
        promocion promoActualizadaService = new promocion(promoId, "Promo Nueva", "Descripcion Nueva"); // Lo que el servicio devuelve

        // Simular el buscar -> modificar (en el test) -> guardar
        when(promocionService.buscarPromocionporid(promoId)).thenReturn(promoOriginal);
        when(promocionService.agregarPromocion(any(promocion.class))).thenReturn(promoActualizadaService); //guarda y devuelve la promo modificada

        
        mockMvc.perform(put("/api/promociones/{id}", promoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(promoActualizadaInput)))
                .andExpect(status().isOk()) // Espera un status 200 OK
                .andExpect(jsonPath("$.titulo").value("Promo Nueva"))
                .andExpect(jsonPath("$.descripcion").value("Descripcion Nueva"));
    }

    @Test
    void actualizarPagosPorId_NotFoundSiPromocionNoExiste() throws Exception {
        
        Long promoId = 99L;
        promocion promoActualizadaInput = new promocion(promoId, "Jugete Promo", "Jugete de perro");

        when(promocionService.buscarPromocionporid(promoId)).thenThrow(new NoSuchElementException());

        
        mockMvc.perform(put("/api/promociones/{id}", promoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(promoActualizadaInput)))
                .andExpect(status().isNotFound()); // Espera un status 404 Not Found
    }

 

    @Test
    void eliminarPromocion_EliminarYRetornarOk() throws Exception {
        
        Long promoId = 1L;
        promocion promoExistente = new promocion(promoId, "A eliminar", "Mensaje");

        when(promocionService.buscarPromocionporid(promoId)).thenReturn(promoExistente); //Simulamos que la encuentra
        doNothing().when(promocionService).eliminarpromocion(promoId); //Simulamos que la eliminación es exitosa

        
        mockMvc.perform(delete("/api/promociones/{id}", promoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera un status 200 OK
                .andExpect(content().string("Promocion  Eliminado Correctamente")); 
    }

    @Test
    void eliminarPromocion_NotFoundSiNoExiste() throws Exception {
        
        Long promoId = 99L; //ID no existente

        when(promocionService.buscarPromocionporid(promoId)).thenThrow(new NoSuchElementException()); //Simula que no se encuentra

        
        mockMvc.perform(delete("/api/promociones/{id}", promoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // Espera un status 404 Not Found
                .andExpect(content().string("Promocion Inexistente"));
    }
}
