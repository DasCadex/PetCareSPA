package com.example.creacionusuario.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.creacionusuario.model.Rol;
import com.example.creacionusuario.repository.RoleRepositoy;

@ExtendWith(MockitoExtension.class)

public class RoleServiceTest {
    @Mock
    private RoleRepositoy repository;

    @InjectMocks
    private RoleService service;

    @Test
    void findall_returnEncontrarTodos() {

        List<Rol> mockList = Arrays.asList(new Rol(1L, "admin", null));

        when(repository.findAll()).thenReturn(mockList);

        List<Rol> result = service.bucarRol();

        assertThat(result).isEqualTo(mockList);

    }

    @Test
    void findById_returnEncontrarPorId() {
        Rol rolMock = new Rol(1L, "admin", null);

        when(repository.findById(1L)).thenReturn(Optional.of(rolMock));

        Rol resultado = service.buscarPorId(1L); // Asumiendo que así se llama el método en tu servicio

        assertThat(resultado).isEqualTo(rolMock);
    }

}
