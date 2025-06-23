package com.microservicios.mascotas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.repository.MascotasRepository;
import com.microservicios.mascotas.webclient.UsuarioClient;

/**
 * Servicio que gestiona la lógica de negocio relacionada con las mascotas.
 * Se encarga de operaciones como listar, agregar, buscar y eliminar mascotas.
 * También valida que el usuario asociado a la mascota exista.
*/

@Service
public class MascotasService {

    @Autowired
    private MascotasRepository mascotasRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    /**
     * Obtiene todas las mascotas registradas en la base de datos.
     *
     * @return Lista de objetos {@link Mascotas}.
    */
    public List<Mascotas> obatenerMascotas(){
        return mascotasRepository.findAll();

    }



    /**
     * Agrega una nueva mascota al sistema. Antes de guardar la mascota,
     * se consulta el microservicio de usuarios para validar que el usuario exista
     * y se asigna su nombre como propietario de la mascota.
     *
     * @param nuevaMascotas La nueva mascota a registrar.
     * @return La mascota guardada en la base de datos.
     * @throws RuntimeException si el usuario no existe o no tiene nombre.
    */
    public Mascotas agregarmascota(Mascotas nuevaMascotas){

        Map<String, Object> usuario = usuarioClient.getUsuarioById(nuevaMascotas.getUsuarioId());
        if (usuario == null || usuario.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado, no puede ingresar una mascota ");
        }

        String nombres =(String) usuario.get("nombres");
         if(nombres==null){
            throw new RuntimeException("el usuario no tiene hombres ");
        }
        nuevaMascotas.setNombredueno(nombres);
        return mascotasRepository.save(nuevaMascotas);
    }




    /**
     * Busca una mascota por su ID.
     *
     * @param id Identificador único de la mascota.
     * @return Objeto {@link Mascotas} si existe, o {@code null} si no se encuentra.
    */
    public Mascotas obtenerporid(Long id){
        return mascotasRepository.findById(id).orElse(null);
    }



    
    /**
     * Elimina una mascota de la base de datos usando su ID.
     *
     * @param id Identificador de la mascota a eliminar.
    */
    public void eliminarMascotas(long id){
        mascotasRepository.deleteById(id);
    }

    
}
