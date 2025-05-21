package com.microservicios.pagos.service;

import java.util.List;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.pagos.model.Pagos;
import com.microservicios.pagos.repository.RepositoryPagos;

@Service
public class PagosService {

    @Autowired
    private RepositoryPagos repositoryPagos;

    public List <Pagos> obtenerPagos(){

        return repositoryPagos.findAll();

    }

    public Pagos agregarpagos (Pagos pagos){

        return repositoryPagos.save(pagos);

    }

    public Pagos obtenerporid(Long id){
        return repositoryPagos.findById(id).orElse(null);
    }

    

}
