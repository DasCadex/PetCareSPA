package com.example.HistorialMedico.Service;

import com.example.HistorialMedico.Model.HistorialMedico;
import com.example.HistorialMedico.Repository.HistorialMedicoRepository;
import com.example.HistorialMedico.WebClient.MascotasClient;
import com.example.HistorialMedico.WebClient.UsuarioClient;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

import jakarta.transaction.Transactional;



/**
 * Servicio que gestiona la lógica de negocio relacionada con los historiales médicos
 * de mascotas, incluyendo validaciones con servicios externos de mascotas y usuarios.
 */
@Service
@Transactional

public class HistorialMedicoService {

    

    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;

    @Autowired
    private MascotasClient mascotasClient;

    @Autowired
    private UsuarioClient usuarioClient;
    

    /**
     * Crea un nuevo historial médico validando la existencia de la mascota y del veterinario.
     * También verifica que el usuario tenga el rol de "Veterinario".
     *
     * @param nuevohistorial Objeto {@link HistorialMedico} a guardar.
     * @return El historial médico guardado.
     * @throws RuntimeException Si la mascota o el usuario no existen, o no cumple con los requisitos.
     */

    public HistorialMedico crearHistorialMedico(HistorialMedico nuevohistorial) {

        Map<String, Object> mascota= mascotasClient.getMascotasById(nuevohistorial.getMascotaid());
        if (mascota == null || mascota.isEmpty()){
            throw new RuntimeException("Mascota no encontra o inexistente");
        }

        Map<String, Object> usuario = usuarioClient.getUsuarioById(nuevohistorial.getUsuarioid());
        if(usuario==null || usuario.isEmpty()){
            throw new RuntimeException("veterinario no existe");
        }

        Map<String, Object> rolMap = (Map<String, Object>) usuario.get("rol");
        if (rolMap == null || !rolMap.get("nombre").toString().equalsIgnoreCase("Veterinario")) {
        throw new RuntimeException("Acceso denegado: el usuario no tiene el rol Veterinario");
        }
        String nombres  = (String) usuario.get("nombres");
        if (nombres==null){
            throw new RuntimeException("nombre del veterinario no encontrado");

        }

        nuevohistorial.setNombreveterinario(nombres);

        String nombrems = (String ) mascota.get("nombre");
        if(nombrems== null){
            throw new RuntimeException("Mascota sin nombre o inexistente ");
        }
        nuevohistorial.setNombremascota(nombrems);




        return historialMedicoRepository.save(nuevohistorial);





       
    }


    /**
     * Obtiene todos los historiales médicos almacenados en la base de datos.
     *
     * @return Lista de historiales médicos.
     */
    public List<HistorialMedico> obtenerTodosLosHistoriales() {
        return historialMedicoRepository.findAll();

    }


    /**
     * Obtiene un historial médico específico por su ID.
     *
     * @param id ID del historial.
     * @return {@link Optional} con el historial si existe, o vacío si no se encuentra.
    */
    public Optional<HistorialMedico> obtenerHistorialMedicoPorId(Long id) {
        return historialMedicoRepository.findById(id);

    }

    
   /**
     * Elimina un historial médico por su ID, si existe.
     *
     * @param id ID del historial a eliminar.
     * @return {@code true} si fue eliminado, {@code false} si no existe.
    */
   public boolean eliminarHistorialMedico(Long id) {
    if (historialMedicoRepository.existsById(id)) {
        historialMedicoRepository.deleteById(id);
        return true;
    }
    return false;
    }



    /**
     * Obtiene todos los historiales médicos registrados por un veterinario específico.
     *
     * @param usuarioid ID del usuario veterinario.
     * @return Lista de historiales médicos creados por ese usuario.
    */
    public List<HistorialMedico> ObtenerSolicitudesPorUsuario(Long usuarioid){
        return historialMedicoRepository.findByUsuarioid(usuarioid);

    
    
    }
    
}
