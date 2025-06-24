package com.example.ordenecompra.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ordenecompra.model.Orden;
import com.example.ordenecompra.repository.OrdenRepository;
import com.example.ordenecompra.service.OrdenService;
import com.example.ordenecompra.webclient.ProductoClient;
import com.example.ordenecompra.webclient.PromocionClient;
import com.example.ordenecompra.webclient.UsuarioClient;

@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private PromocionClient promocionClient;

    @InjectMocks
    private OrdenService ordenService;

    @Test
    void buscarOrden_returnListFromRepository() {
        List<Orden> mockList = Arrays.asList(
            new Orden(1L, 10L, 20L, "producto1", "usuario1", 100.0, 2, 1L, "promo1")
        );

        when(ordenRepository.findAll()).thenReturn(mockList);

        List<Orden> result = ordenService.buscarOrden();

        assertThat(result).isEqualTo(mockList);
    }

    @Test
    void buscarPorId_returnOrden() {
        Orden orden = new Orden(1L, 10L, 20L, "producto1", "usuario1", 100.0, 2, 1L, "promo1");

        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));

        Orden result = ordenService.buscarPorid(1L);

        assertThat(result).isEqualTo(orden);
    }

    @Test
    void guardarOrdenCompre_returnSavedOrden() {
        Orden nuevaOrden = new Orden(null, 10L, 20L, null, null, null, 2, 1L, null);

        // Mock para clientes web
        when(promocionClient.getPromocionById(1L)).thenReturn(Map.of("descripcion", "promo1"));
        when(usuarioClient.getUsuarioById(10L)).thenReturn(Map.of("nombres", "usuario1"));
        when(productoClient.getProductoById(20L)).thenReturn(Map.of("nombre_producto", "producto1", "precio_producto", 100.0));

        // Mock save
        Orden ordenGuardada = new Orden(1L, 10L, 20L, "producto1", "usuario1", 100.0, 2, 1L, "promo1");
        when(ordenRepository.save(nuevaOrden)).thenReturn(ordenGuardada);

        Orden result = ordenService.guardarOrdenCompre(nuevaOrden);

        assertThat(result).isEqualTo(ordenGuardada);
    }

    @Test
    void eliminarOrden_shouldCallRepositoryDelete() {
        Orden orden = new Orden(1L, 10L, 20L, "producto1", "usuario1", 100.0, 2, 1L, "promo1");

        when(ordenRepository.findById(1L)).thenReturn(Optional.of(orden));

        ordenService.eliminarOrden(1L);

        verify(ordenRepository).delete(orden);
    }

}
