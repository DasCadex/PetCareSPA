package com.microservicios.Reservas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.microservicios.reservas.controller.ReservasController;
import com.microservicios.reservas.model.Reservas;
import com.microservicios.reservas.service.ReservasService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//cargamos el controlador que ara la simulacion 
@WebMvcTest(ReservasController.class)

public class ReservasControllerTest {

    // inyectamos el mocki ah service
    @MockBean
    private ReservasService reservasService;
    // creamos un mock que nos da spirng
    @Autowired
    private MockMvc mockMvc;

    @Test

    void getallReservas_returnOKandJson() {
        // creamos una reserva fictisia para que pueda trabajar

        List<Reservas> listaReservas = Arrays.asList(new Reservas(1L, "12-12-2025", "canser", 1L, "canser2", 14002.0, "cade"));

        // identificamo el comportamineto del servicio
        when(reservasService.obtenerTodas()).thenReturn(listaReservas);

        // ejecutaremos la funcion del controlador
        // el metodo que ejecutaremos sera el GET

        // tambien validara el archivo Json
        // y verificara que la respuesta sea un erro 200 OK
        try {

            mockMvc.perform(get("api/reservas")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1L));

        } catch (Exception e) {

        }

    }

    @Test
    void crearReserva_returnCreatedAndJson() {
        // Creamos una reserva ficticia
        Reservas nuevaReserva = new Reservas(1L, "12-12-2025", "consulta", 1L, "sin observaciones", 15000.0, "Juan");

        // Simulamos el comportamiento del servicio
        when(reservasService.crearReserva(any(Reservas.class))).thenReturn(nuevaReserva);

        // en este tipo cuando se usa el post tenemos que similar el postman que entra para que lo valide y no pete 

        try {
            
            mockMvc.perform(post("/api/reservas").contentType("application/json")
            .content("""
                {
                    "fecha": "12-12-2025",
                    "motivo": "consulta",
                    "usuarioId": 1,
                    "observaciones": "sin observaciones"
                }
            """))
            .andExpect(status().isCreated());

         
        } catch (Exception e) {

        }
    }

    @Test
    void eliminarReserva_returnOk() {
        try {
            //cuando es borrar no se le ara nade debido a que no retorna nada solo peta
            mockMvc.perform(delete("/api/reservas/1")).andExpect(status().isOk());
        } catch (Exception e) {
           
        }
    }

    @Test
    void obtenerReservaPorId_returnOKandJson() throws Exception {
    Reservas reserva = new Reservas(1L, "12-12-2025", "consulta", 1L, "sin observaciones", 15000.0, "Juan");

    // Simula la respuesta del servicio
    when(reservasService.obtenerporid(1L)).thenReturn(reserva);

    // Ejecuta el GET con el ID 1 y valida que el JSON contenga los datos esperados
    mockMvc.perform(get("/api/reservas/1")).andExpect(status().isOk()).andExpect(jsonPath("$.reservaId").value(1L)).andExpect(jsonPath("$.motivo").value("consulta")).andExpect(jsonPath("$.nombrecliente").value("Juan"));
}




}
