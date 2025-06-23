package com.example.HistorialMedico.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.HistorialMedico.Controller.HistorialMedicoController;
import com.example.HistorialMedico.Model.HistorialMedico;
import com.example.HistorialMedico.Service.HistorialMedicoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(HistorialMedicoController.class)
public class HistorialMedicoControllerTest {

    @Autowired

    private MockMvc mockMvc;

    @MockBean

    private HistorialMedicoService historialService;

    @Autowired

    private ObjectMapper objectMapper;

    @Test

    void obtenerTodosLosHistoriales() throws Exception {
        List<HistorialMedico> lista = Arrays.asList(new HistorialMedico());
        when(historialService.obtenerTodosLosHistoriales()).thenReturn(lista);

        mockMvc.perform(get("/api/historialmedico"))
            .andExpect(status().isOk());

    }


    @Test

    void obtenerHistorialPorId() throws Exception {
        HistorialMedico historial = new HistorialMedico();
        historial.setIdhistorial(1L);

        when(historialService.obtenerHistorialMedicoPorId(1L)).thenReturn(Optional.of(historial));

        mockMvc.perform(get("/api/historialmedico/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idhistorial").value(1L));
    }

    @Test

    void obtenerHistorialPorId_noExistente() throws Exception {
        when(historialService.obtenerHistorialMedicoPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/historialmedico/2"))
            .andExpect(status().isNotFound());
    }

    @Test

    void eliminarHistorial_existente() throws Exception {
        when(historialService.eliminarHistorialMedico(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/historialmedico/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarHistorial_noExistente() throws Exception {
        when(historialService.eliminarHistorialMedico(100L)).thenReturn(false);

        mockMvc.perform(delete("/api/historialmedico/100"))
            .andExpect(status().isNotFound());
    }
}