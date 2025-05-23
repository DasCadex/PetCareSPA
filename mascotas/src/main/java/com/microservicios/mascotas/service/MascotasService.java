package com.microservicios.mascotas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.repository.MascotasRepository;
import com.microservicios.mascotas.webclient.UsuarioClient;


@Service
public class MascotasService {

    @Autowired
    private MascotasRepository mascotasRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Mascotas> obatenerMascotas(){
        return mascotasRepository.findAll();

    }

    public Mascotas agregarmascota(Mascotas nuevaMascotas){

        Map<String, Object> usuario = usuarioClient.getUsuarioById(nuevaMascotas.getUsuarioId());
        if (usuario == null || usuario.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado, no puede ingresar una mascota ");
        }

        String nombres =(String) usuario.get("nombres");
         if(nombres==null){
            throw new RuntimeException("el usuario no tiene hombres ");
        }
        nuevaMascotas.setNombredueno(nombres);; 

        


        return mascotasRepository.save(nuevaMascotas);





    }

    public Mascotas obtenerporid(Long id){
        return mascotasRepository.findById(id).orElse(null);
    }

    public void eliminarMascotas(long id){
        mascotasRepository.deleteById(id);
    }

    
}
