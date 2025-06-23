package com.example.productos.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException; 

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import com.example.productos.service.ProductoService;

@ExtendWith(MockitoExtension.class) 
public class ProductoServiceTest {

    @InjectMocks 
    private ProductoService productoService;

    @Mock 
    private ProductoRepository productoRepository;



    @Test
    void agregarProducto_debeGuardarYRetornarProducto() {

       //datos de entrada simulando que entra un producto
        //el id del producto es null porque se espera que la base de datos lo genere al guardarlo
        Producto nuevoProducto = new Producto(null, 7, "Sachet de perro", "DogShow", 7);
       
        //Este producto ya tiene el ID asignado, simulando lo que el repositorio devolvería.  
        Producto productoGuardado = new Producto(19L, 7, "Sachet de perro", "DogShow", 7);
       
        //Cuando el método save de productoRepository sea llamado con cualquier objeto de tipo Producto devuelve el productoGuardado
        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado); 
       
        //este pone el metodo a probar
        Producto resultado = productoService.agregarProducto(nuevoProducto);
       
        //este es para verificar que no sea null el objeto que devuelve
        assertNotNull(resultado, "El producto guardado no deberia ser nulo");
       
       
       
        //este es para confirmar que los datos sean los que pusimos anteriormente y verificar       
        assertEquals(19L, resultado.getId(), "El ID del producto debe coincidir con el asignado.");
        assertEquals("Sachet de perro", resultado.getNombre_producto(), "El nombre del producto debe coincidir.");
        assertEquals("DogShow", resultado.getMarca_producto(), "La marca del producto debe coincidir.");
        assertEquals(7, resultado.getStock(), "El stock del producto debe coincidir.");
        assertEquals(7, resultado.getPrecio_producto(), "El precio del producto debe coincidir.");

}

    @Test
    void buscarProductoPorId_debeRetornarProductoCuandoExiste() {
        
        Long productoId = 1L; // ID del producto a buscar
        Producto productoExistente = new Producto(1L, 5, "Comida Perro", "Pedigree", 13); 

        
        // Cuando se llame a findById con el ID especifico devolverá un Optional que contiene el producto
        when(productoRepository.findById(eq(productoId))).thenReturn(Optional.of(productoExistente));

        
        Producto resultado = productoService.buscarProductoPorId(productoId);

       //este es para confirmar que los datos sean los que pusimos anteriormente y verificar   
        assertNotNull(resultado, "El producto no debería ser nulo.");
        assertEquals(productoId, resultado.getId(), "El ID del producto debe coincidir.");
        assertEquals("Comida Perro", resultado.getNombre_producto(), "El nombre del producto debe coincidir");
        assertEquals("Pedigree", resultado.getMarca_producto(), "La marca del producto debe coincidir");
        assertEquals(13, resultado.getPrecio_producto(), "El precio del producto debe coincidir");
        assertEquals(5, resultado.getStock(), "El stock del producto debe coincidir");

       
    }

    @Test
    void buscarProductoPorId_ExcepcionNoExiste() {
        
        Long productoId = 99L; // ID de un producto que no existe

        // Cuando se llame a findById con un ID no existente devuelve un Optional vacio
        when(productoRepository.findById(eq(productoId))).thenReturn(Optional.empty());

       
        // Esperamos que se tire una NoSuchElementException porque el método del servicio usa get en un Optional vacio si no encuentra el producto
        assertThrows(NoSuchElementException.class, () -> {
            productoService.buscarProductoPorId(productoId);
        }, "Debe lanzar NoSuchElementException si el producto no se encuentra.");

       
    }

  

    @Test
    void buscarProducto_ListaDeProductos() {
        //Simulacion de una lista de productos 
        Producto prod1 = new Producto(1L, 10, "Comida gato", "CatShow", 120);
        Producto prod2 = new Producto(2L, 5, "Juegete de perro", "Fisher Price", 50);
        List<Producto> listaProductos = Arrays.asList(prod1, prod2);

        // Simulamos el comportamiento del repositorio:
        // Cuando se llame a findAll devuelve la lista simulada.
        when(productoRepository.findAll()).thenReturn(listaProductos);

      
        List<Producto> resultado = productoService.buscarProducto();

        
        assertNotNull(resultado, "La lista de productos no debería ser nula.");
        assertFalse(resultado.isEmpty(), "La lista de productos no debería estar vacía.");
        assertEquals(2, resultado.size(), "La lista debe contener 2 productos.");
        assertEquals("Comida gato", resultado.get(0).getNombre_producto());
        assertEquals("Juegete de perro", resultado.get(1).getNombre_producto());

       
    }

    @Test
    void buscarProducto_ListaVaciaSiNoHayProductos() {

        //este es una Simulacion del comportamiento de el repositorio
        // Cuando se llame a findAll devuelve una lista vacía.
        when(productoRepository.findAll()).thenReturn(Collections.emptyList());

       
        List<Producto> resultado = productoService.buscarProducto();

       
        assertNotNull(resultado, "La lista de productos no debería ser nula (aunque este vacia)");
        assertTrue(resultado.isEmpty(), "La lista de productos debería estar vacia");
        assertEquals(0, resultado.size(), "La lista debe contener 0 productos");      
    }


    @Test
    void eliminarProducto_debeLlamarDeleteById() {
        
        Long productoId = 1L; // ID del producto a eliminar

        
        productoService.eliminarProducto(productoId);

        
    }
}