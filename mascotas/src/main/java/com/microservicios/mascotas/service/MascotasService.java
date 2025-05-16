package com.microservicios.mascotas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.repository.MascotasRepository;

@Service
public class MascotasService {

    @Autowired
    private MascotasRepository mascotasRepository;

    public List<Mascotas> obatenerMascotas(){
        return mascotasRepository.findAll();

    }

    public Mascotas agregarmascota(Mascotas mascotas){

        return mascotasRepository.save(mascotas);
    }

    public Mascotas obtenerporid(Long id){
        return mascotasRepository.findById(id).orElse(null);
    }

    public void eliminarMascotas(long id){
        mascotasRepository.deleteById(id);
    }

    
}
