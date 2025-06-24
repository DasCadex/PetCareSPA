package com.example.ordenecompra.service;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordenecompra.model.Orden;
import com.example.ordenecompra.repository.OrdenRepository;
import com.example.ordenecompra.webclient.ProductoClient;
import com.example.ordenecompra.webclient.PromocionClient;
import com.example.ordenecompra.webclient.UsuarioClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private PromocionClient promocionClient;

    public List<Orden> buscarOrden() {//nos permnite buscar todas las ordenes que tenga 
        return ordenRepository.findAll();
    }

    public Orden buscarPorid(Long id) {//nos permite buscar la orden de compra a traves de su id 
        return ordenRepository.findById(id).get();
    }

    public Orden guardarOrdenCompre(Orden nuevaOrden) {

        Map<String, Object> promocion = promocionClient.getPromocionById(nuevaOrden.getPromocionid());//verificamos la promocion de la id si exite 
        if (promocion == null || promocion.isEmpty()) {
            throw new RuntimeException("promocion no encontrada ");
        }

        Map<String, Object> usuario = usuarioClient.getUsuarioById(nuevaOrden.getUsuarioId());//verifica si el usuaruio exite si no dara el error 
        if (usuario == null || usuario.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado, no se puede crear la orden de compra");
        }

        Map<String, Object> producto = productoClient.getProductoById(nuevaOrden.getProductoId());// obtienes el id del
                                                                                                  // prodcuto llamando a
                                                                                                  // la clase y lo
                                                                                                  // guarda y si no
                                                                                                  // existe dara el
                                                                                                  // error

        if (producto == null || producto.isEmpty()) {
            throw new RuntimeException("Producto no encontrado, no se puede crear la orden de compra");
        }

        System.out.println(promocion);

        String promocionif = (String) promocion.get("descripcion");//llamamos la decripcion de la 
        if (promocionif == null) {
            throw new RuntimeException("promocion no en vigencian");
        }
        nuevaOrden.setPromocionif(promocionif);

        String nombreProducto = (String) producto.get("nombre_producto"); // le damos la key pára saber que datos quiere
                                                                          // sacar
        if (nombreProducto == null) {
            throw new RuntimeException("El producto no contiene un campo 'nombre'");
        }

        nuevaOrden.setNombreProducto(nombreProducto);// guardamos el nombre del porducto que llamos

        Object preciObj = producto.get("precio_producto");
        if (preciObj == null) {// llamo a la clase que tenga el precio producto
            throw new RuntimeException("el prodcuto no tiene precio ");
        }

        Double precioProducto;
        try {
            precioProducto = Double.valueOf(preciObj.toString());// si es sztring lo convertimos adecimal

        } catch (NumberFormatException e) {
            throw new RuntimeException("Error al convertir el precio del producto a número");

        }
        nuevaOrden.setPrecioProducto(precioProducto);

        String nombres = (String) usuario.get("nombres");
        if (nombres == null) {
            throw new RuntimeException("el usuario no tiene hombres ");
        }
        nuevaOrden.setNombres(nombres);

        if (nuevaOrden.getNombreProducto() == null || nuevaOrden.getNombreProducto().isEmpty()) {
            throw new RuntimeException("El nombre del producto no puede ser nulo o vacío");
        }

        return ordenRepository.save(nuevaOrden);

    }

    public void eliminarOrden(Long id_orden) {//nos permites eliminar una orden a traves de su id 
        Orden orden = ordenRepository.findById(id_orden).orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        ordenRepository.delete(orden);
    }

    public Double calcularTotalOrdenes() {//nos permite calcullar el valor de todo
        List<Orden> ordenes = ordenRepository.findAll();// esto nos permite calcular el valor de los productos y el
                                                        // precio
        return ordenes.stream()
                .mapToDouble(orden -> orden.getPrecioProducto() * orden.getCantidad())
                .sum();
    }

    public List<Orden> buscarPorUsuario(Long usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId);
    }

}
