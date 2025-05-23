package com.microservicios.promociones_ofertas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.promociones_ofertas.model.promocion;
import com.microservicios.promociones_ofertas.repository.promocionRepository;

@Service
public class PromocionService {

    @Autowired
    private promocionRepository promocionRepository;

    public List <promocion> buscartodasPromociones(){
        return promocionRepository.findAll();
    }

    public promocion buscarPromocionporid(long id){
        
        return promocionRepository.findById(id).get();
    }

    public promocion agregarPromocion(promocion promocion){
        return promocionRepository.save(promocion);

    }

    public  void eliminarpromocion(Long id){
        promocionRepository.deleteById(id);
    }
}
