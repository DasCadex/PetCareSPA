package com.microservicios.pagos.Controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicios.pagos.controller.PagosController;
import com.microservicios.pagos.model.Pagos;
import com.microservicios.pagos.service.PagosService;

@WebMvcTest(PagosController.class)
public class PagosControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean 
    private PagosService pagosService;

    @Autowired
    private ObjectMapper objectMapper; 

   

    @Test
    void listapagos_200OkConListaDePagos() throws Exception {
        //Datos de entrada
        Pagos pago1 = new Pagos(1L, 561.6, "DentaSticks", "Pipe", 12L);
        Pagos pago2 = new Pagos(2L, 193.2, "Juegete de Hueso", "Fernanda", 23L);

        //devuelve la lista simulada
        when(pagosService.obtenerPagos()).thenReturn(Arrays.asList(pago1, pago2));

        //Realizar un GET 
        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk()) // Espera un 200 OK
                //estos de aqui son para verificar que devuelva los valores indicados
                .andExpect(jsonPath("$[0].pagoId").value(1L)) 
                .andExpect(jsonPath("$[0].nombrecliente").value("Pipe")) 
                .andExpect(jsonPath("$[1].pagoId").value(2L)) 
                .andExpect(jsonPath("$[1].nombrecliente").value("Fernanda")); 
    }

    @Test
    void listapagos_204CuandoNoHayPagos() throws Exception {
        //devuelve una lista vacia
        when(pagosService.obtenerPagos()).thenReturn(Collections.emptyList());

        // Realizar el GET y verifica la respuesta
        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isNoContent()); // Espera un 204 No Content
    }

  

    @Test
    void obtenerPagoPorId_existente_200YPago() throws Exception {
        //simulando un pago especifico
        Pagos pagoExistente = new Pagos(3L, 399.0, "Factura 129", "Victor", 52L);

        //devuelve el pago simulado
        when(pagosService.obtenerporid(eq(3L))).thenReturn(pagoExistente);

        // Realizar el GET por ID y verifica la respuesta
        mockMvc.perform(get("/api/pagos/3"))
                .andExpect(status().isOk()) // Espera un 200 OK
                 //estos de aqui son para verificar que devuelva los valores indicados
                .andExpect(jsonPath("$.pagoId").value(3L)) 
                .andExpect(jsonPath("$.nombrecliente").value("Victor")); 
    }

    @Test
    void obtenerPagoPorId_noExistente_200YNull() throws Exception {
        //devuelve null cuando llama a 99L
        when(pagosService.obtenerporid(eq(99L))).thenReturn(null);

        // Realizar el GET por ID y verificar la respuesta
        mockMvc.perform(get("/api/pagos/99"))
                .andExpect(status().isOk()) //devuelve Pagos si es null el estado es 200
                .andExpect(jsonPath("$").doesNotExist()); // Verifica que el cuerpo de la respuesta este vacío o sea null
    }

    

    @Test
    void crearPago_exito_201PagoGuardado() throws Exception {
        // Datos de entrada un nuevo pago para enviar al cuerpo de la peticion
        Pagos nuevoPago = new Pagos(0L, null, "Nueva Compra", null, 10L); 
        
        // Datos de salida como se veria el pago despues de ser guardado por el servicio
        Pagos pagoGuardado = new Pagos(10L, 10.0, "Jugete de raton", "Messi", 10L);

        //cuando se llame a agregarpagos con cualquier Pagos devuelve el pago guardado
        when(pagosService.agregarpagos(any(Pagos.class))).thenReturn(pagoGuardado);

        // Realizar el POST y verificar la respuesta
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON) 
                .content(objectMapper.writeValueAsString(nuevoPago))) 
                .andExpect(status().isCreated()) // Espera un 201 
                 //estos de aqui son para verificar que devuelva los valores indicados
                .andExpect(jsonPath("$.pagoId").value(10L)) 
                .andExpect(jsonPath("$.monto").value(10.0)) 
                .andExpect(jsonPath("$.nombrecliente").value("Messi")); 
    }

    @Test
    void crearPago_fallaServicio_400MensajeError() throws Exception {
        // Datos de entrada para el pago que causaran un error
        Pagos pagoConError = new Pagos(999L, null, "Compra fallida", null, 999L);
        String errorMessage = "Id de la orden de compra no encontrado o inexistente";

        //cuando se llame a agregarpagos lanza un error
        when(pagosService.agregarpagos(any(Pagos.class))).thenThrow(new RuntimeException(errorMessage));

        // Realizar el POST y verificar la respuesta
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoConError)))
                .andExpect(status().isBadRequest()) // Espera un 400 
                .andExpect(jsonPath("$").value(errorMessage)); //Mensaje de error en el cuerpo de la respuesta
    }
}

