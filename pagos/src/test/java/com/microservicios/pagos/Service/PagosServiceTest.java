package com.microservicios.pagos.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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

import com.microservicios.pagos.model.Pagos;
import com.microservicios.pagos.repository.RepositoryPagos;
import com.microservicios.pagos.service.PagosService; 
import com.microservicios.pagos.webclient.OrdenClient;

@ExtendWith(MockitoExtension.class) 
public class PagosServiceTest {

    @InjectMocks 
    private PagosService pagosService; 

    @Mock 
    private RepositoryPagos repositoryPagos; 

    @Mock 
    private OrdenClient ordenClient; 

 

    @Test
    void agregarpagos_OrdenValida() {
       
        //un nuevo pago de entrada con un ordenCompraId que usaremos para la simulacion
        Pagos nuevoPago = new Pagos();
        nuevoPago.setOrdenCompraId(10L);
        nuevoPago.setDescripcion("Pago exitoso");
        
        //Simulamos la respuesta que el OrdenClient devolveria al consultar la orden
        //Este Map representa el JSON que estaria en el microservicio de ordenes
        Map<String, Object> respuestaOrden = Map.of(
            "id", 10L, 
            "precioProducto", 22.75, 
            "nombres", "Agustin Cardenas" 
        );
    
        //Simulamos el comportamiento de OrdenClient Cuando ordenClientgetOrdenById es llamado con el ID de orden 101L simulamos que devuelve una respuesta respuestaOrden
        when(ordenClient.getOrdenById(eq(10L))).thenReturn(respuestaOrden);

        //Simulamos el comportamiento de RepositoryPagos
        Pagos pagoGuardadoSimulado = new Pagos(10L, 22.75, "Pago exitoso", "Agustin Cardenas", 123L);
        when(repositoryPagos.save(any(Pagos.class))).thenReturn(pagoGuardadoSimulado);

        // Ejecutamos el método agregarpagos del servicio
        Pagos resultado = pagosService.agregarpagos(nuevoPago);
        
        //Verificamos que el resultado no sea nulo.
        assertNotNull(resultado, "El pago guardado no deberia ser nulo");

        //Verificamos que el monto y el nombre del cliente se hayan asignado correctamente
        assertEquals(22.75, resultado.getMonto(), 0.001, "El monto del pago debe coincidir con el de la orden");
        assertEquals("Agustin Cardenas", resultado.getNombrecliente(), "El nombre del cliente debe coincidir con el de la orden");

        //Verificamos que el ID de la orden de compra se mantuvo
        assertEquals(123L, resultado.getOrdenCompraId(), "El ID de la orden de compra debe ser el original");
    
    }

    @Test
    void agregarpagos_fallaCuandoOrdenNoEncontrada() {
       
        Pagos nuevoPago = new Pagos();
        nuevoPago.setOrdenCompraId(999L); // ID de una orden que no existe

        //Simulamos que OrdenClient devuelve un Map nulo o vacio
        when(ordenClient.getOrdenById(eq(999L))).thenReturn(null);

        //Esperamos que se lance una RuntimeException 
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagosService.agregarpagos(nuevoPago);
        }, "Debe hacer RuntimeException si la orden no es encontrada");

        assertEquals("Id de la orden de compra no encontrado o inexistente", exception.getMessage());
        
    }

    @Test
    void agregarpagos_MontoEsInvalido() {
        
        Pagos nuevoPago = new Pagos();
        nuevoPago.setOrdenCompraId(152L);

        //Simulamos una respuesta de orden donde precioProducto es nulo o no es un numero
        Map<String, Object> respuestaOrdenInvalidaMonto = Map.of(
            "id", 152L, 
            "precioProducto", "valor_no_numerico", // Simula un valor no valido xd
            "nombres", "Alexander Pato"
        );

        when(ordenClient.getOrdenById(eq(152L))).thenReturn(respuestaOrdenInvalidaMonto);

        //aca ejecutamos y verificamos
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagosService.agregarpagos(nuevoPago);
        }, "se debe lanzar un RuntimeException si es invalido");

        assertEquals("El monto no tiene cantidad o es invalido",exception.getMessage());
    }

    @Test
    void agregarpagos_NombreClienteEsNulo() {
        
        Pagos nuevoPago = new Pagos();
        nuevoPago.setOrdenCompraId(165L);

        // Simulamos una respuesta de orden donde 'nombres' es nulo.
        Map<String, Object> respuestaOrdenInvalidaNombre = Map.of(
            "id", 165L, 
            "precioProducto", 21.73, 
            // nombres es null
            "Orden", 12 
        );

        when(ordenClient.getOrdenById(eq(165L))).thenReturn(respuestaOrdenInvalidaNombre);

       //ejecutamos y verificamos
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagosService.agregarpagos(nuevoPago);
        }, "Debe lanzar RuntimeException si el nombre del cliente es nulo");

        assertEquals("El producto no contiene un campo nombres", exception.getMessage());
    }


    @Test
    void obtenerPagos_debeRetornarListaDePagos() {
    
        // Simulamos una lista de pagos 
        Pagos pago1 = new Pagos(14L, 70.0, "Alimento para gato", "Nikito", 15L);
        Pagos pago2 = new Pagos(21L, 50.0, "Alimento de Canario", "Kratos", 21L);
        List<Pagos> pagosSimulados = Arrays.asList(pago1, pago2);

        
        //Cuando se llame a repositoryPagos findAll devuelve la lista simulada
        when(repositoryPagos.findAll()).thenReturn(pagosSimulados);

       
        List<Pagos> resultado = pagosService.obtenerPagos();

        
        assertNotNull(resultado, "La lista de pagos no deberia ser nula");
        assertEquals(2, resultado.size(), "La lista debe contener 2 pagos");
        assertEquals("Nikito", resultado.get(0).getNombrecliente());
        assertEquals("Alimento de Canario", resultado.get(1).getDescripcion());
    }


    @Test
    void obtenerPagos_debeRetornarListaVaciaSiNoHayPagos() {
       
        // Simulamos que el repositorio devuelve una lista vacia
        when(repositoryPagos.findAll()).thenReturn(Collections.emptyList());

        
        List<Pagos> resultado = pagosService.obtenerPagos();

        
        assertNotNull(resultado, "La lista de pagos no deberia ser nula");
        assertTrue(resultado.isEmpty(), "La lista de pagos deberia estar vacia");
        assertEquals(0, resultado.size(), "La lista debe contener 0 pagos");

      ;
    }



    @Test
    void obtenerporid_debeRetornarPagoCuandoExiste() {
       
        Long pagoId = 5L;
        Pagos pagoExistente = new Pagos(pagoId, 150.0, "Factura", "Asura", 15L);

        // Simulamos que repositoryPagosfindById devuelve el pago existente
        when(repositoryPagos.findById(eq(pagoId))).thenReturn(Optional.of(pagoExistente));

        
        Pagos resultado = pagosService.obtenerporid(pagoId);

        
        assertNotNull(resultado, "El pago no debería ser nulo");
        assertEquals(pagoId, resultado.getPagoId(), "El ID del pago debe coincidir");
        assertEquals("Asura", resultado.getNombrecliente(), "El nombre del cliente debe coincidir");

       
    }

    @Test
    void obtenerporid_debeRetornarNullCuandoNoExiste() {
        
        Long pagoId = 99L;

        // Simulamos que repositoryPagosfindById devuelve un Optional vacio
        when(repositoryPagos.findById(eq(pagoId))).thenReturn(Optional.empty());

        
        Pagos resultado = pagosService.obtenerporid(pagoId);

        
        //Aca esperamos que el resultado sea null
        assertEquals(null, resultado, "El pago debe ser nulo si no se encuentra");

    }
}


