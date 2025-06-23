package com.example.Notificaciones.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional; 

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Notificaciones.Model.Notificaciones;
import com.example.Notificaciones.Repository.NotificacionesRepository;
import com.example.Notificaciones.WebClient.PagosClient;
import com.example.Notificaciones.WebClient.UsuarioClient;



@ExtendWith(MockitoExtension.class)
public class NotifiacionesServiceTest {

    @InjectMocks
    private NotificacionesService notificacionesServices;

    @Mock 
    private NotificacionesRepository notificacionesRepository;

    @Mock 
    private UsuarioClient usuarioClient;

    @Mock 
    private PagosClient pagosClient;


    @Test
    void crearNotificacion_ConDatosValidos() {
        
        //Datos de entrada para la nueva notificacion
        Notificaciones notificacionEntrada = new Notificaciones();
        notificacionEntrada.setIdusuario(10L);
        notificacionEntrada.setIdpago(20L);
        notificacionEntrada.setMensaje("Pago completado ");
        notificacionEntrada.setFechaCreacion("2025-06-23");

        //Simulamos la respuesta del UsuarioClient
        Map<String, Object> usuarioResponse = Map.of("id", 10L, "nombres", "Amumu Jg");
        when(usuarioClient.getUsuarioById(eq(10L))).thenReturn(usuarioResponse);

        //Simulamos la respuesta del PagosClient
        Map<String, Object> pagosResponse = Map.of("id", 20L, "monto", 120.0);
        when(pagosClient.getPagosById(eq(20L))).thenReturn(pagosResponse);

        //Simulamos el comportamiento del repositorio al guardar
        //el servicio modificara el objeto notificacionEntrada añadiendo nombreAdmin y luego el repositorio lo guarde devolviendo el mismo objeto o uno nuevo con ID si fuera el caso real
        Notificaciones notificacionGuardada = new Notificaciones(
            1L, 20L, "Pago completado", "2025-06-23", 10L, "Amumu Jg"
        );
        when(notificacionesRepository.save(any(Notificaciones.class))).thenReturn(notificacionGuardada);

        
        Notificaciones resultado = notificacionesServices.crearNotificacion(notificacionEntrada);

        
        assertNotNull(resultado, "La notificación resultante no debe ser nula");
        assertEquals(1L, resultado.getIdnotificacion(), "El ID de notificación debería ser el asignado");
        assertEquals("Amumu Jg", resultado.getNombreAdmin(), "El nombre del admin debe ser el del usuario");
        assertEquals(10L, resultado.getIdusuario(), "El ID de usuario debe ser el original");
        assertEquals(20L, resultado.getIdpago(), "El ID de pago debe ser el original");
        assertEquals("Pago completado", resultado.getMensaje(), "El mensaje debe coincidir");

    }


    @Test
    void crearNotificacion_UsuarioNoEncontrado() {
        
        Notificaciones notificacionEntrada = new Notificaciones();
        notificacionEntrada.setIdusuario(99L); // Usuario inventado que no existe
        notificacionEntrada.setIdpago(20L);

        //Simulamos que usuarioClient devuelve null 
        when(usuarioClient.getUsuarioById(eq(99L))).thenReturn(null);

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificacionesServices.crearNotificacion(notificacionEntrada);
        }, "Debe lanzar RuntimeException si el usuario no es encontrado");

        assertEquals("Cliente no encontrado no se puede crear la orden de compra", exception.getMessage());
        
    }

    @Test
    void crearNotificacion_PagoNoEncontrado() {
        
        Notificaciones notificacionEntrada = new Notificaciones();
        notificacionEntrada.setIdusuario(10L);
        notificacionEntrada.setIdpago(99L); // Pago no que no existe

        Map<String, Object> usuarioResponse = Map.of("id", 10L, "nombres", "Lee Sin");
        when(usuarioClient.getUsuarioById(eq(10L))).thenReturn(usuarioResponse);

        //Simulamos que pagosClient devuelve null
        when(pagosClient.getPagosById(eq(99L))).thenReturn(null);

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificacionesServices.crearNotificacion(notificacionEntrada);
        }, "Debe lanzar RuntimeException si el pago no es encontrado");

        assertEquals("pago no encontrado ", exception.getMessage()); 
        verify(usuarioClient, times(1)).getUsuarioById(eq(10L));
        verify(pagosClient, times(1)).getPagosById(eq(99L));
        verify(notificacionesRepository, times(0)).save(any(Notificaciones.class));
    }

    @Test
    void crearNotificacion_NombreAdminEsNulo() {
        
        Notificaciones notificacionEntrada = new Notificaciones();
        notificacionEntrada.setIdusuario(10L);
        notificacionEntrada.setIdpago(20L);

        // Usuario existe pero su nombre es nulo 
        Map<String, Object> usuarioResponse = Map.of("id", 10L, "Nombre", "valor"); 
        when(usuarioClient.getUsuarioById(eq(10L))).thenReturn(usuarioResponse);

        Map<String, Object> pagosResponse = Map.of("id", 20L, "monto", 150.0);
        when(pagosClient.getPagosById(eq(20L))).thenReturn(pagosResponse);

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificacionesServices.crearNotificacion(notificacionEntrada);
        }, "Debe lanzar RuntimeException si el nombre del admin es nulo");

        assertEquals("Nombre no encontrado", exception.getMessage());
    
    }

    

    @Test
    void obtenerTodasLasNotificaciones_debeRetornarListaDeNotificaciones() {
        
        Notificaciones n1 = new Notificaciones(1L, 1L, "Ola Bro", "2025-01-01", 12L, "Admin1");
        Notificaciones n2 = new Notificaciones(2L, 2L, "Adios Bro", "2025-01-02", 50L, "Admin2");
        List<Notificaciones> notificacionesSimuladas = Arrays.asList(n1, n2);

        when(notificacionesRepository.findAll()).thenReturn(notificacionesSimuladas);

       
        List<Notificaciones> resultado = notificacionesServices.obtenerTodasLasNotificaciones();

        
        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(2, resultado.size(), "Debe retornar dos notificaciones");
        assertEquals("Admin1", resultado.get(0).getNombreAdmin());
        assertEquals("Adios Bro", resultado.get(1).getMensaje());

     
    }

    @Test
    void obtenerTodasLasNotificaciones_SiNoHayNotificacionesListaVacia() {
        
        when(notificacionesRepository.findAll()).thenReturn(Collections.emptyList());

        
        List<Notificaciones> resultado = notificacionesServices.obtenerTodasLasNotificaciones();

        
        assertNotNull(resultado, "La lista no debe ser nula");
        assertTrue(resultado.isEmpty(), "La lista debe estar vacía");
        assertEquals(0, resultado.size());

        
    }

   

    @Test
    void obtenerNotificacionPorId_NotificacionCuandoExiste() {
        
        Long idExistente = 5L;
        Notificaciones notificacionExistente = new Notificaciones(idExistente, 52L, "Hola soy admin", "2025-03-15", 30L, "AdminPro");

        when(notificacionesRepository.findById(eq(idExistente))).thenReturn(Optional.of(notificacionExistente));

        
        Optional<Notificaciones> resultado = notificacionesServices.obtenerNotificacionPorId(idExistente);

        
        assertTrue(resultado.isPresent(), "La notificación debe estar presente");
        assertEquals(idExistente, resultado.get().getIdnotificacion());
        assertEquals("AdminPro", resultado.get().getNombreAdmin());

        
    }

    @Test
    void obtenerNotificacionPorId_VacioCuandoNoExiste() {
        
        Long idNoExistente = 99L;
        when(notificacionesRepository.findById(eq(idNoExistente))).thenReturn(Optional.empty());

        
        Optional<Notificaciones> resultado = notificacionesServices.obtenerNotificacionPorId(idNoExistente);

        
        assertTrue(resultado.isEmpty(), "La notificación no debe estar presente");

  
    }

   

    @Test
    void eliminarNotificacion_EliminarCuandoExiste() {
        
        Long idParaEliminar = 7L;
        when(notificacionesRepository.existsById(eq(idParaEliminar))).thenReturn(true);

       
        boolean eliminado = notificacionesServices.eliminarNotificacion(idParaEliminar);

        
        assertTrue(eliminado, "Debe retornar true si la notificación fue eliminada");
    }

    @Test
    void eliminarNotificacion_noEliminarCuandoNoExiste() {
        
        Long idNoExistente = 88L;
        when(notificacionesRepository.existsById(eq(idNoExistente))).thenReturn(false);

        
        boolean eliminado = notificacionesServices.eliminarNotificacion(idNoExistente);

        
        assertTrue(!eliminado, "Debe retornar false si la notificación no existe");
       
    }
}