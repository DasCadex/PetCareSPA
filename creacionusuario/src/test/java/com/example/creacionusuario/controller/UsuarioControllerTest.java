package com.example.creacionusuario.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.creacionusuario.dto.UsuarioDTO;
import com.example.creacionusuario.model.Rol;
import com.example.creacionusuario.model.Usuario;
import com.example.creacionusuario.service.RoleService;
import com.example.creacionusuario.service.UsuarioService;

//cargamos que que simuadro todo
@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)

public class UsuarioControllerTest {

    // inyectamos el mock en el service

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private RoleService roleService;

    // creamos el mock que nos da Spring

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getallUsuarios_returnOkandJason() {
        Rol rol = new Rol(1L, "admin", null);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(1L);
        dto.setCorreo("original@mail.com");
        dto.setNombres("fernando");
        dto.setApellido("villalobos");
        dto.setRol(rol);

        List<UsuarioDTO> listaUsuarios = Arrays.asList(dto);

        when(usuarioService.obtenerUsuarios()).thenReturn(listaUsuarios);

        try {
            mockMvc.perform(get("/api/v1/user")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1L));

        } catch (Exception e) {

        }
    }

    @Test
    void crearUsuario_returnOkandJason() {
        Rol rol = new Rol(1L, "admin", null);
        Usuario nuevoUsuario = new Usuario(1L, "cade", "1234", "cade@gmail.com", "fernando", "villalobos", rol);
        // todo esto se hace debido a que ocuipamios el dto en nuetra clase

        when(usuarioService.crearUsuario(anyString(), anyString(), anyString(), anyString(), anyString(), anyLong()))
                .thenReturn(nuevoUsuario);

        try {
            // tenemos que agregar el pos que reciviura para que lo guarde . oviamente sin
            // tener el id del usuario

            mockMvc.perform(post("/api/v1/users").contentType("application/json")
                    .content("""
                                {
                                    "username": "12-12-2025",
                                    "password": "consulta",
                                    "correo": "dadada",
                                    "nombres": "sin observaciones",
                                    "apellido":"dadada",
                                    "rol":{
                                        "id":1

                                    }

                                }
                            """))
                    .andExpect(status().isCreated());

        } catch (Exception e) {

        }

    }

    @Test
    void Eliminarusurio_returnOkandJson() {

        // en este caso para hacer las pruebas soloasignamos el id y probar si lo
        // elimina
        Long idUsuario = 1L;

        try {
            mockMvc.perform(delete("/api/v1/users/{id}", idUsuario)).andExpect(status().isOk());
        } catch (Exception e) {

        }
    }

    @Test
    void getallByid_returnOkandJson() {
        Long idUsuario = 1L;

        Rol rol = new Rol(1L, "admin", null);
        Usuario usuario = new Usuario(idUsuario, "cade", "1234", "cade@gmail.com", "Fernando", "Villalobos", rol);

        when(usuarioService.buscarPorId(idUsuario)).thenReturn(usuario);

        try {
            mockMvc.perform(get("/api/v1/user/{id}", idUsuario)).andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        } catch (Exception e) {

        }
    }

    @Test
    void editar_returnOkandJason() {
        //asignanomos el id del asuario
        Long idUsuario = 1L;
        Rol rol = new Rol(1L, "admin", null);
        //creamo el usuario nuevo 
        Usuario usuarioActualizado = new Usuario(idUsuario, "cade", "nueva1234", "nuevo@mail.com", "Fernando","Villalobos", rol);

        when(usuarioService.actualizarUsuario(eq(idUsuario), any(Usuario.class))).thenReturn(usuarioActualizado);

        try {
            mockMvc.perform(put("/api/v1/users/{id}", idUsuario)
                    .contentType("application/json")
                    .content("""
                            {
                              "username": "cade",
                              "password": "nueva1234",
                              "correo": "nuevo@mail.com",
                              "nombres": "Fernando",
                              "apellido": "Villalobos",
                              "rol": {
                                "id": 1
                              }
                            }
                            """))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
