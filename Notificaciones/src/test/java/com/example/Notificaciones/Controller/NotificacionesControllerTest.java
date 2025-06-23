package com.example.Notificaciones.Controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Notificaciones.Model.Notificaciones;
import com.example.Notificaciones.Service.NotificacionesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(NotificacionesController.class) 
public class NotificacionesControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean 
    private NotificacionesService notificacionesService;

    @Autowired
    private ObjectMapper objectMapper; 


    @Test
    void crearNotificacion_201() throws Exception {
        
        Notificaciones inputNotificacion = new Notificaciones();
        inputNotificacion.setIdusuario(1L);
        inputNotificacion.setIdpago(2L);
        inputNotificacion.setMensaje("Tu pago ha sido procesado exitosamente.");

       
        Notificaciones notificacionGuardada = new Notificaciones();
        notificacionGuardada.setIdnotificacion(5L); 
        notificacionGuardada.setIdusuario(1L);
        notificacionGuardada.setIdpago(2L);
        notificacionGuardada.setMensaje("Tu pago ha sido procesado exitosamente.");
        notificacionGuardada.setNombreAdmin("NikoRex"); 

        
        //aqui llamamos a crearNotificacion con cualquier objeto Notificaciones retorna notificacionGuardada
        when(notificacionesService.crearNotificacion(any(Notificaciones.class))).thenReturn(notificacionGuardada);

        //Simula la petición POST y verifica la respuesta
        mockMvc.perform(post("/api/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputNotificacion)))
                .andExpect(status().isCreated()) 
                .andExpect(jsonPath("$.idnotificacion").value(5L)) 
                .andExpect(jsonPath("$.nombreAdmin").value("NikoRex"));              
    }

    @Test
    void crearNotificacion_manejaErrorDeServicio_400() throws Exception {
        // Datos de entrada que provocarian un error en el servicio (ej: usuario no encontrado)
        Notificaciones inputNotificacion = new Notificaciones();
        inputNotificacion.setIdusuario(999L);
        inputNotificacion.setMensaje("Intento fallido.");

        // Simula que el servicio lanza una problematica
        when(notificacionesService.crearNotificacion(any(Notificaciones.class)))
            .thenThrow(new IllegalArgumentException("Usuario o pago no válidos."));

        // Simula la petición y espera un 400 
        mockMvc.perform(post("/api/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputNotificacion)))
                .andExpect(status().isBadRequest()) //404
                .andExpect(content().string("Usuario o pago no válidos.")); 

        
    }

   

    @Test
    void obtenerTodasLasNotificaciones_200yLista() throws Exception {
        // Datos simulados de notificaciones
        Notificaciones notificacion1 = new Notificaciones();
        notificacion1.setIdnotificacion(1L);
        notificacion1.setMensaje("Mensaje Uno");

        Notificaciones notificacion2 = new Notificaciones();
        notificacion2.setIdnotificacion(2L);
        notificacion2.setMensaje("Mensaje Dos");

        List<Notificaciones> listaSimulada = Arrays.asList(notificacion1, notificacion2);

        //devuelve la lista simulada
        when(notificacionesService.obtenerTodasLasNotificaciones()).thenReturn(listaSimulada);

        // Simula el GET y verifica la respuesta
        mockMvc.perform(get("/api/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Verifica que la lista tenga 2 elementos
                .andExpect(jsonPath("$[0].idnotificacion").value(1L))
                .andExpect(jsonPath("$[1].mensaje").value("Mensaje Dos"));

       
    }

    @Test
    void obtenerTodasLasNotificaciones_listaVacia_200() throws Exception {
        //devuelve una lista vacia
        when(notificacionesService.obtenerTodasLasNotificaciones()).thenReturn(Collections.emptyList());

        // Simula el GET y verifica la respuesta
        mockMvc.perform(get("/api/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Verifica que la lista está vacía      
    }



    @Test
    void obtenerNotificacionPorId_existente_200() throws Exception {
        // Notificacion simulada para una busqueda por ID 
        Notificaciones notificacionEncontrada = new Notificaciones();
        notificacionEncontrada.setIdnotificacion(1L); 
        notificacionEncontrada.setMensaje("Notificación por ID 1");
        

        //si se busca ID 1, devuelve la notificacion
        when(notificacionesService.obtenerNotificacionPorId(1L)).thenReturn(Optional.of(notificacionEncontrada));

        //Simula la petición GET por ID y verifica la respuesta
        mockMvc.perform(get("/api/notificaciones/1"))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.idnotificacion").value(1L))
                .andExpect(jsonPath("$.mensaje").value("Notificación por ID 1")); 
    }

    @Test
    void obtenerNotificacionPorId_noExistente_404() throws Exception {
        //si se busca ID 99, devuelve vacio
        when(notificacionesService.obtenerNotificacionPorId(99L)).thenReturn(Optional.empty());

        // GET por ID y espera un 404 Not Found
        mockMvc.perform(get("/api/notificaciones/99"))
                .andExpect(status().isNotFound());    
    }

   

    @Test
    void eliminarNotificacion_existente_204() throws Exception {
        //si se intenta eliminar ID 1 da true (éxito)
        when(notificacionesService.eliminarNotificacion(1L)).thenReturn(true);

        //DELETE espera un 204 No Content
        mockMvc.perform(delete("/api/notificaciones/1"))
                .andExpect(status().isNoContent());   
    }

    @Test
    void eliminarNotificacion_noExistente_404() throws Exception {
        //si se intenta eliminar ID 99 da false (no encontrado)
        when(notificacionesService.eliminarNotificacion(99L)).thenReturn(false);

        // Simula la peticion DELETE y espera un 404 No encontrado
        mockMvc.perform(delete("/api/notificaciones/99"))
                .andExpect(status().isNotFound());
    }
}
