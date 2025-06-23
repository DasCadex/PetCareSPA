package com.example.productos.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException; 

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.productos.controller.ProductoController;
import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;


@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean 
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

 

    @Test
    void listaProducto_200OkConListaDeProductos() throws Exception {
        //Preparamos datos simulados de productos
        Producto prod1 = new Producto(1L, 10, "Suplemento de perro Omega 3", "Spes", 13);
        Producto prod2 = new Producto(2L, 5, "Jugete de gato", "Jugetosa", 10);
        List<Producto> productosSimulados = Arrays.asList(prod1, prod2);

        //Configuramos el mock del servicio para que  cuando se llame a buscarProducto devuelva nuestra lista simulada
        when(productoService.buscarProducto()).thenReturn(productosSimulados);

        //Realizamos la petición GET y verificamos la respuesta
        mockMvc.perform(get("/api/productos") 
                .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk()) // Espera un 200 OK
                .andExpect(jsonPath("$[0].id").value(1L)) 
                .andExpect(jsonPath("$[0].nombre_producto").value("Suplemento de perro Omega 3")) 
                .andExpect(jsonPath("$[1].id").value(2L)) 
                .andExpect(jsonPath("$[1].nombre_producto").value("Jugete de gato")); 
    }

    @Test
    void listaProducto_204NoContentCuandoNoHayProductos() throws Exception {
        //Configuramos el mock del servicio para que devuelva una lista la cual estas vacia
        when(productoService.buscarProducto()).thenReturn(Collections.emptyList());

        //Realizamos la peticion GET y esperamos un 204 No Content
        mockMvc.perform(get("/api/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Espera un 204 No Content
    }

 

    @Test
    void buscarPorId_200OkConProductoExistente() throws Exception {
        //preparamos un producto simulado que el servicio encontrara
        Long productoId = 1L;
        Producto productoExistente = new Producto(productoId, 8, "Maro Snacks", "Milk Bone", 10);

        //configuramos el mock del servicio para cuando se busque a buscarProductoPorId con el ID devuelva el producto
        when(productoService.buscarProductoPorId(eq(productoId))).thenReturn(productoExistente);

        //Realizamos la petición GET y verificamos el producto devuelto
        mockMvc.perform(get("/api/productos/{id}", productoId) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera un 200 OK
                .andExpect(jsonPath("$.id").value(productoId)) 
                .andExpect(jsonPath("$.nombre_producto").value("Maro Snacks"))
                .andExpect(jsonPath("$.marca_producto").value("Milk Bone"));
    }

    @Test
    void buscarPorId_404NotFoundCuandoProductoNoExiste() throws Exception {
        // configuramos para que suelte un exception y NoSuchElementException si findById no encuentra nada
        Long productoId = 99L;
        when(productoService.buscarProductoPorId(eq(productoId)))
            .thenThrow(new NoSuchElementException("Producto no encontrado con ID: " + productoId));

        //aca realizamos la peticion GET y esperamos un 404 Not Found
        mockMvc.perform(get("/api/productos/{id}", productoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Espera un 404 Not Found
    }

    @Test
    void guardarProducto_201ConElProductoGuardado() throws Exception {

        //Producto de entrada que se enviara producto sin id
        Producto nuevoProducto = new Producto(null, 15, "DentaLife", "Purina", 14);
        //Producto simulado que el servicio devolvera despues de guardar 
        Producto productoGuardado = new Producto(31L, 15, "DentaLife", "Purina", 14);

        //cuando se llame a agregarProducto devuelve el producto guardado
        when(productoService.agregarProducto(any(Producto.class))).thenReturn(productoGuardado);

        //Realizamos la petición POST y verificamos la respuesta
        mockMvc.perform(post("/api/productos") 
                .contentType(MediaType.APPLICATION_JSON) 
                .content(objectMapper.writeValueAsString(nuevoProducto))) 
                .andExpect(status().isCreated()) // Espera 201 Created
                .andExpect(jsonPath("$.id").value(31L)) 
                .andExpect(jsonPath("$.nombre_producto").value("DentaLife"))
                .andExpect(jsonPath("$.marca_producto").value("Purina"));
    }
    
   
    @Test
    void guardarProducto_400BadRequestPorValidacion() throws Exception {
        //el producto cuenta con un campo obligatorio nulo o en blanco
        Producto productoInvalido = new Producto(null, 10, "", "MarcaGenerica", 50);

        //Realizamos la peticion POST con datos invalidos
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoInvalido)))
                .andExpect(status().isBadRequest()); // Espera un 400 Bad Request
    }


    @Test
    void borrarPacientePorId_204NoContentCuandoEliminaExitosamente() throws Exception {
        //Preparamos un producto que ya existe y lo eliminaremos
        Long productoId = 4L;
        Producto productoAEliminar = new Producto(productoId, 4, "Correa para Perro", "Generica", 12);

        //Cuando se busque el producto por ID devuelve el producto existente
        when(productoService.buscarProductoPorId(eq(productoId))).thenReturn(productoAEliminar);
        //Cuando se llame a eliminarProducto no hara nada 
        doNothing().when(productoService).eliminarProducto(eq(productoId));

        //aca Realizaremos la peticion DELETE y esperamos un 204 No Content
        mockMvc.perform(delete("/api/productos/{id}", productoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Espera 204 No Content
    }

    @Test
    void borrarPacientePorId_404CuandoelProductoNoExiste() throws Exception {
        //configuramos para que el mock falle a proposito
        Long productoId = 99L;
        //Cuando se intente buscar el producto lanzara una excepcion simulando que no lo encontro
        when(productoService.buscarProductoPorId(eq(productoId)))
            .thenThrow(new NoSuchElementException("Producto no encontrado para eliminar"));

        //aca realizamos la petición DELETE y esperamos un 404 Not Found
        mockMvc.perform(delete("/api/productos/{id}", productoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Espera un 404 Not Found
    }

    

    @Test
    void actualizarProductoPorId_200ConelProductoActualizado() throws Exception {
        
        Long productoId = 5L;
        //el producto existe en la base de datos antes de la actualizacion
        Producto productoOriginal = new Producto(productoId, 10, "Arena de gato", "Fit Formula", 24);
        //Datos del producto que se enviaran en la peticion PUT para actualizar
        Producto productoActualizado = new Producto(null, 8, "Arena de gato Premiun", "Fit Formula", 30); // ID null aqui se toma de la URL
        //Producto simulado que el servicio devolvera despues de la actualizacion
        Producto productoDespuesActualizacion = new Producto(productoId, 8, "Arena de gato Premiun", "Fit Formula", 30);

        
        when(productoService.buscarProductoPorId(eq(productoId))).thenReturn(productoOriginal);
        //Cuando se guarde el producto despues de la actualizacion devuelve el producto ya actualizado
        when(productoService.agregarProducto(any(Producto.class))).thenReturn(productoDespuesActualizacion);

        //Realizamos la peticion PUT
        mockMvc.perform(put("/api/productos/{id}", productoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoActualizado)))
                .andExpect(status().isOk()) //200 OK
                .andExpect(jsonPath("$.id").value(productoId))
                .andExpect(jsonPath("$.nombre_producto").value("Arena de gato Premiun"))
                .andExpect(jsonPath("$.precio_producto").value(30));
    }

    @Test
    void actualizarProductoPorId_404CuandoelProductoNoExiste() throws Exception {
        
        Long productoId = 99L;
        Producto productoActualizadoDatos = new Producto(null, 1, "ItemFalsisimo", "MarcaGenerica", 100);

        //este hace que la busqueda del producto lanza una excepcion
        when(productoService.buscarProductoPorId(eq(productoId)))
            .thenThrow(new NoSuchElementException("Producto no encontrado para actualizar"));

        //Realizamos la peticion PUT
        mockMvc.perform(put("/api/productos/{id}", productoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoActualizadoDatos)))
                .andExpect(status().isNotFound()); //404 Not Found
    }
}
