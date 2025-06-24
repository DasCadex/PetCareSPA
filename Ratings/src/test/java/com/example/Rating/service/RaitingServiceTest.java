package com.example.Rating.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Rating.Model.Rating;
import com.example.Rating.Repository.RatingRepository;
import com.example.Rating.Service.RatingService;
import com.example.Rating.WebClient.ProductoClient;
import com.example.Rating.WebClient.UsuarioClient;

@ExtendWith(MockitoExtension.class)

public class RaitingServiceTest {

    @Mock
    private RatingRepository repository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private RatingService service;

    @Test
    void findAll_returnTodos() {

        List<Rating> mockList = Arrays.asList(new Rating(1L, 2L, 3L, 1231, "no me echare fullstack"));
        // definimos el comportamientp

        when(repository.findAll()).thenReturn(mockList);

        List<Rating> result = service.obtenerTodosLosRatings();

        assertThat(result).isEqualTo(mockList);

    }

    @Test
    void save_returnGuardarrating() {
        // Creamos el objeto que queremos guardar
        Rating nuevoRating = new Rating(1L, 2L, 3L, 5, null);

        // tenemos que tener un usuario que similar que exista
        when(usuarioClient.getUsuarioById(2L)).thenReturn(Map.of("id", 2L));

        // lo mismo con el producto crear uno fictisio
        when(productoClient.getProductoById(3L)).thenReturn(Map.of("nombre_producto", "no echarme fullstack"));

        // Creamos el objeto esperado con el nombre del producto ya seteado
        Rating esperado = new Rating(1L, 2L, 3L, 5, "no echarme fullstack");

        // Definimos el comportamiento del repositorio
        when(repository.save(esperado)).thenReturn(esperado);

        // Ejecutamos el método a probar
        Rating result = service.guardarRating(nuevoRating);

        // Verificamos que devuelve el mismo objeto
        assertThat(result).isEqualTo(esperado);
    }

    @Test
    void deleteByid_returnPetar() {
        // Crear un objeto Rating ficticio
        Rating rating = new Rating(1L, 2L, 3L, 5, "no echarme fullstack");

        // Simular que el rating existe al buscar por ID
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(rating));

        // No necesitamos simular delete, porque no devuelve nada (vo

        // Ejecutar el método a probar
        service.eliminarRaiting(1L);

    }

    @Test
    void findById_returnBuscarPorid() {
        // Crear un objeto Rating ficticio
        Rating rating = new Rating(1L, 2L, 3L, 5, "Producto X");

        // Simular que el rating existe al buscar por ID
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(rating));

        // Ejecutar el método a probar
        Rating resultado = service.obtenerRatingPorId(1L).orElse(null);

        // Verificar que lo encontrado es el mismo
        assertThat(resultado).isEqualTo(rating);
    }

    @Test
    void findByUsuarioId_returnListaRatings() {
        // Lista simulada de ratings para un usuario
        List<Rating> mockList = List.of(
                new Rating(1L, 5L, 10L, 4, "Producto A"),
                new Rating(2L, 5L, 11L, 5, "Producto B"));

        // Simular comportamiento del repositorio
        when(repository.findByIdusuario(5L)).thenReturn(mockList);

        // Ejecutar el método a probar
        List<Rating> resultado = service.obtenerPorUsuario(5L);

        // Verificar que devuelve la lista simulada
        assertThat(resultado).isEqualTo(mockList);
    }

}
