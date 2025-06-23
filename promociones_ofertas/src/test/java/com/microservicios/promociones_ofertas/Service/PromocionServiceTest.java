package com.microservicios.promociones_ofertas.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservicios.promociones_ofertas.model.promocion;
import com.microservicios.promociones_ofertas.repository.promocionRepository;
import com.microservicios.promociones_ofertas.service.PromocionService;

@ExtendWith(MockitoExtension.class) 
public class PromocionServiceTest {

    @InjectMocks 
    private PromocionService promocionService;

    @Mock 
    private promocionRepository promocionRepository;

   

    @Test
    void buscartodasPromociones_ListaDePromociones() {
        
        //Simulamos una lista de promociones que el repositorio devolveria
        promocion promo1 = new promocion(1L, "Descuento Verano", "10% de descuento en todos los productos");
        promocion promo2 = new promocion(2L, "Envío Gratis", "Envío gratuito en compras superiores a $50");
        List<promocion> promocionesSimuladas = Arrays.asList(promo1, promo2);

        // Cuando se llame a promocionRepositoryfindAll devuelve la lista simulada
        when(promocionRepository.findAll()).thenReturn(promocionesSimuladas);

        
        List<promocion> resultado = promocionService.buscartodasPromociones();

        
        assertNotNull(resultado, "La lista de promociones no debería ser nula");
        assertEquals(2, resultado.size(), "La lista debe contener 2 promociones");
        assertEquals("Descuento Verano", resultado.get(0).getTitulo());
        assertEquals("Envío gratuito en compras superiores a $50", resultado.get(1).getDescripcion());

    }

    @Test
    void buscartodasPromociones_ListaVaciaSiNoHayPromociones() {
        
        //Simulamos que el repositorio devuelve una lista vacia
        when(promocionRepository.findAll()).thenReturn(Collections.emptyList());

        
        List<promocion> resultado = promocionService.buscartodasPromociones();

        
        assertNotNull(resultado, "La lista de promociones no debería ser nula (aunque esté vacía).");
        assertTrue(resultado.isEmpty(), "La lista de promociones debería estar vacía.");
        assertEquals(0, resultado.size(), "La lista debe contener 0 promociones.");


    }

    

    @Test
    void buscarPromocionporid_PromocionCuandoExiste() {
        
        Long promocionId = 1L;
        promocion promocionExistente = new promocion(promocionId, "Promo Navidad", "20% en juguetes");

        //Simulamos que promocionRepositoryfindById devuelve la promocion existente
        when(promocionRepository.findById(eq(promocionId))).thenReturn(Optional.of(promocionExistente));

        
        promocion resultado = promocionService.buscarPromocionporid(promocionId);

        
        assertNotNull(resultado, "La promocion no debería ser nula");
        assertEquals(promocionId, resultado.getIdpromocion(), "El ID de la promocion debe coincidir");
        assertEquals("Promo Navidad", resultado.getTitulo(), "El título debe coincidir");


    }

    @Test
    void buscarPromocionporid_ExcepcionCuandoNoExiste() {
        
        Long promocionId = 99L; // Un ID no existente

        //Simulamos que promocionRepositoryfindById devuelve un Optional vacio
        when(promocionRepository.findById(eq(promocionId))).thenReturn(Optional.empty());

       
        //Esperamos que se lance una NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> {
            promocionService.buscarPromocionporid(promocionId);
        }, "Debe lanzar NoSuchElementException si la promocion no es encontrada");

     
    }



    @Test
    void agregarPromocion_GuardarNuevaPromocion() {
        
        promocion nuevaPromo = new promocion(null, "Promo Cyber", "Ofertas en Comidas"); 
        promocion promoGuardada = new promocion(3L, "Promo Cyber", "Ofertas en Comidas"); 

        
        //simulamos que devuelve promoGuardada como si se le asignara un ID
        when(promocionRepository.save(any(promocion.class))).thenReturn(promoGuardada);

        
        promocion resultado = promocionService.agregarPromocion(nuevaPromo);

        
        assertNotNull(resultado, "La promocion guardada no deberia ser nula");
        assertEquals(3L, resultado.getIdpromocion(), "Debe asignarse un ID a la nueva promocion");
        assertEquals("Promo Cyber", resultado.getTitulo());

    }

    @Test
    void agregarPromocion_ActualizarPromocionExistente() {
        
        promocion promoExistente = new promocion(1L, "Descuento Verano", "10% de descuento en todos los productos");
        promoExistente.setDescripcion("20% de descuento en Jugetes de playa"); 

        //Cuando promocionRepositorysave es llamado con la promocion modificada
        //simulamos que devuelve la misma promoción como si la actualización fuera exitosa
        when(promocionRepository.save(any(promocion.class))).thenReturn(promoExistente);

        // ACT (Ejecución):
        promocion resultado = promocionService.agregarPromocion(promoExistente);

        // ASSERT (Verificación):
        assertNotNull(resultado, "La promocion actualizada no deberia ser nula.");
        assertEquals(1L, resultado.getIdpromocion());
        assertEquals("20% de descuento en Jugetes de playa", resultado.getDescripcion());

    }

  

    @Test
    void eliminarpromocion_debeEliminarPromocionPorId() {
        
        Long idParaEliminar = 1L;

        //deleteById no haga nada cuando se le llame con el ID especificado
        
        doNothing().when(promocionRepository).deleteById(eq(idParaEliminar));

        
        promocionService.eliminarpromocion(idParaEliminar);

        

    }

    

    
}