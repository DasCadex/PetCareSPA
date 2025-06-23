package com.microservicios.mascotas.service;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.repository.MascotasRepository;
import com.microservicios.mascotas.webclient.UsuarioClient;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceTest {

    @InjectMocks
    private MascotasService mascotasService;

    @Mock
    private MascotasRepository mascotasRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Test
    void agregarmascota_ok() {
        Mascotas mascota = new Mascotas();
        mascota.setUsuarioId(1L);

        Map<String, Object> usuario = Map.of("nombres", "Carlos");

        when(usuarioClient.getUsuarioById(1L)).thenReturn(usuario);
        when(mascotasRepository.save(any(Mascotas.class))).thenReturn(mascota);

        Mascotas resultado = mascotasService.agregarmascota(mascota);

        assertEquals("Carlos", resultado.getNombredueno());
        verify(mascotasRepository).save(mascota);
    }

    @Test
    void agregarmascota_usuarioNoExiste() {
        Mascotas mascota = new Mascotas();
        mascota.setUsuarioId(100L);

        when(usuarioClient.getUsuarioById(100L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            mascotasService.agregarmascota(mascota);
        });

        assertEquals("Cliente no encontrado, no puede ingresar una mascota ", ex.getMessage());
    }

    @Test
    void obtenerporid_ok() {
        Mascotas mascota = new Mascotas();
        mascota.setMascotasId(10L);

        when(mascotasRepository.findById(10L)).thenReturn(Optional.of(mascota));

        Mascotas resultado = mascotasService.obtenerporid(10L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getMascotasId());
    }

    @Test
    void eliminarMascotas_ok() {
        doNothing().when(mascotasRepository).deleteById(5L);

        assertDoesNotThrow(() -> mascotasService.eliminarMascotas(5L));
        verify(mascotasRepository).deleteById(5L);
    }

    @Test
    void CrearMascotaok(){

        //datos de entrada
        Mascotas mascotas = new  Mascotas();
        mascotas.setUsuarioId(1l);

        

        // Simular respuesta del cliente de usuarios
        Map<String, Object> rol = Map.of("nombre", "Cliente");
        Map<String, Object> usuario = Map.of("nombres", "Hugo", "rol", rol);
        when(usuarioClient.getUsuarioById(1L)).thenReturn(usuario);
        

        when(mascotasRepository.save(any(Mascotas.class))).thenReturn(mascotas);

        Mascotas resultado = mascotasService.agregarmascota(mascotas);

        assertEquals("Hugo", resultado.getNombredueno());

    }
}
