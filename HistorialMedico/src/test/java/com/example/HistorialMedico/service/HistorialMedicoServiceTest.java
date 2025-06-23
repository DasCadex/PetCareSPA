package com.example.HistorialMedico.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.HistorialMedico.Model.HistorialMedico;
import com.example.HistorialMedico.Repository.HistorialMedicoRepository;
import com.example.HistorialMedico.Service.HistorialMedicoService;
import com.example.HistorialMedico.WebClient.MascotasClient;
import com.example.HistorialMedico.WebClient.UsuarioClient;

@ExtendWith(MockitoExtension.class)
public class HistorialMedicoServiceTest {

    @InjectMocks
    private HistorialMedicoService historialMedicoService;

    @Mock
    private HistorialMedicoRepository historialMedicoRepository;

    @Mock
    private MascotasClient mascotasClient;

    @Mock
    private UsuarioClient usuarioClient;

    
    @Test
    void crearHistorialMedico_ok() {

        // Datos de entrada
        HistorialMedico historial = new HistorialMedico();
        historial.setUsuarioid(1L);
        historial.setMascotaid(2L);

        // Simular respuesta del cliente de mascotas
        Map<String, Object> mascota = Map.of("nombre", "ELPEPE");
        when(mascotasClient.getMascotasById(2L)).thenReturn(mascota);

        // Simular respuesta del cliente de usuarios
        Map<String, Object> rol = Map.of("nombre", "Veterinario");
        Map<String, Object> usuario = Map.of("nombres", "Dr. Hugo", "rol", rol);
        when(usuarioClient.getUsuarioById(1L)).thenReturn(usuario);

        // Simular guardado en la base de datos
        when(historialMedicoRepository.save(any(HistorialMedico.class))).thenReturn(historial);

        // Ejecutar
        HistorialMedico resultado = historialMedicoService.crearHistorialMedico(historial);

        // Verificar
        assertEquals("ELPEPE", resultado.getNombremascota());
        assertEquals("Dr. Hugo", resultado.getNombreveterinario());

    }
}
