package com.microservicios.pagos.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.pagos.model.Pagos;
import com.microservicios.pagos.repository.RepositoryPagos;
import com.microservicios.pagos.webclient.OrdenClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagosService {

    @Autowired
    private RepositoryPagos repositoryPagos;

    @Autowired
    private OrdenClient ordenClient;

    public List <Pagos> obtenerPagos(){

        return repositoryPagos.findAll();

    }

    public Pagos agregarpagos (Pagos nuevopago){
   
        Map<String, Object> orden= ordenClient.getOrdenById(nuevopago.getOrdenCompraId());
        System.out.println("Orden recibida del microservicio: " + orden);

        if (orden == null || orden.isEmpty()) {
            throw new RuntimeException("Id de la orden de compra no encontrado o inexistente");
        }

        Object montoObj = orden.get("precioProducto");
        Double monto = null;
        if (montoObj instanceof Number) {
            monto = ((Number) montoObj).doubleValue();
        }
        if (monto == null) {
            throw new RuntimeException("El monto no tiene cantidad o es inválido");
        }
        nuevopago.setMonto(monto);

        Object nombreObj = orden.get("nombres");
        String nombrecliente = (nombreObj != null) ? nombreObj.toString() : null;
        if (nombrecliente == null) {
            throw new RuntimeException("El producto no contiene un campo 'nombres'");
        }
        nuevopago.setNombrecliente(nombrecliente);

        return repositoryPagos.save(nuevopago);

}
   

    

    public Pagos obtenerporid(Long id){
        return repositoryPagos.findById(id).orElse(null);
    }

    

}
