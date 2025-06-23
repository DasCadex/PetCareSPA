package com.example.Soporte.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Soporte.Model.Soporte;
import com.example.Soporte.Repository.SoporteRepository;
import com.example.Soporte.Service.SoporteService;
import com.example.Soporte.WebClient.UsuarioClient;



@ExtendWith(MockitoExtension.class)
public class SoporteServiceTest {

    @Mock
    private SoporteRepository soporteRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private SoporteService soporteService;

    @Test
    void crearSolicitud_retornaSolicitudCreada() {
        Soporte soporte = new Soporte(null, "", "", "Mensaje de prueba", "2024-01-01", "pendiente", "consulta", 1L);
        Map<String, Object> usuarioMock = new HashMap<>();
        usuarioMock.put("username", "soporteUser");
        usuarioMock.put("correo", "soporte@email.com");
        usuarioMock.put("rol", Map.of("nombre", "Soporte y administrador de sistemas"));

        when(usuarioClient.getUsuarioById(1L)).thenReturn(usuarioMock);
        when(soporteRepository.save(any())).thenReturn(soporte);

        Soporte resultado = soporteService.crearSolicitud(soporte);

        assertThat(resultado).isEqualTo(soporte);
    }

    @Test
    void obtenerTodasLasSolicitudes_retornaLista() {
        List<Soporte> lista = List.of(new Soporte(1L, "Soporte1", "soporte1@mail.com", "msg", "2024", "nuevo", "consulta", 1L));
        when(soporteRepository.findAll()).thenReturn(lista);

        List<Soporte> resultado = soporteService.obtenerTodasLasSolicitudes();

        assertThat(resultado).isEqualTo(lista);
    }

    @Test
    void obtenerSolicitudPorId_retornaSolicitud() {
        Optional<Soporte> soporte = Optional.of(new Soporte(1L, "usuario", "email", "mensaje", "fecha", "estado", "tipo", 1L));
        when(soporteRepository.findById(1L)).thenReturn(soporte);

        Optional<Soporte> resultado = soporteService.obtenerSolicitudPorId(1L);

        assertThat(resultado).isEqualTo(soporte);
    }

    @Test
    void eliminarSolicitud_existente_retornaTrue() {
        when(soporteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(soporteRepository).deleteById(1L);

        boolean resultado = soporteService.eliminarSolicitud(1L);

        assertThat(resultado).isTrue();
    }

    @Test
    void obtenerSolicitudesPorUsuario_retornaLista() {
        List<Soporte> lista = List.of(new Soporte(1L, "usuario", "email", "mensaje", "fecha", "estado", "tipo", 1L));
        when(soporteRepository.findByUsuarioId(1L)).thenReturn(lista);

        List<Soporte> resultado = soporteService.obtenerSolicitudesPorUsuario(1L);

        assertThat(resultado).isEqualTo(lista);
    }
}
