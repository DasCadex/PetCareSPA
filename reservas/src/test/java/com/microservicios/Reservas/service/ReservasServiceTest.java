package com.microservicios.Reservas.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import com.microservicios.reservas.model.Reservas;
import com.microservicios.reservas.repository.ReservaReposiry;
import com.microservicios.reservas.service.ReservasService;
import com.microservicios.reservas.webclient.UsuarioClient;

@ExtendWith(MockitoExtension.class) // habilita el uso de mockito

public class ReservasServiceTest {

    @Mock
    private UsuarioClient usuarioClient;

    @Mock // declaramos a mockito
    private ReservaReposiry repository;// clonamos el repositorio

    @InjectMocks // inyectamos el mockito a la prueba
    private ReservasService service; // clonamos el service

    @Test // identificamos la variable test donde trabajaremos }

    void findall_returnListFromRepositori() {
        List<Reservas> mockList = Arrays.asList(new Reservas(1L, "12-12-2025", "canser", 1L, "canser2", 14002.0, "cade"));

        when(repository.findAll()).thenReturn(mockList);

        List<Reservas> result = service.obtenerTodas();

        assertThat(result).isEqualTo(mockList);

    }

    // en este caso como estamos guardado datos tenemos que traer al usuario cliente
    // de donde saca el nombre y el id
    @Test
    void save_returnSavedReserva() {
        Reservas nuevaReservas = new Reservas(1L, "12-12-2025", "canser", 1L, "canser2", 14002.0, "cade");

        // tenemos que definir el comportamiento tambien del mock en el usuario clietn
        when(usuarioClient.getUsuarioById(1L)).thenReturn(Map.of("nombres", "cade"));

        // definimos el comportamiento del mock en el repositorio
        when(repository.save(nuevaReservas)).thenReturn(nuevaReservas);

        // ejecutamos el metodo

        Reservas result = service.crearReserva(nuevaReservas);

        // comprueba que devuelva el mismo
        assertThat(result).isSameAs(nuevaReservas);

    }

    @Test
    void findByid_returnReservaId() {

        Reservas buscarId = new Reservas(1L, "12-12-2025", "canser", 1L, "canser2", 14002.0, "cade");

        // simulamos el repositoria para buscar por id

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(buscarId));

        // ejecutamos el metodo

        Reservas result = service.obtenerporid(1L);

        // comprueva que devuelva lo mismo

        assertThat(result).isNotNull();
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        // Act
        service.eliminar(1L);

        // Assert
        verify(repository).deleteById(1L); // Verifica que el método fue llamado con el ID correcto
    }

}
