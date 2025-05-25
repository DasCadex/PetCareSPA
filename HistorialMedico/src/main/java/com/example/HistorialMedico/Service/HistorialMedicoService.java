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

@Service
@Transactional

public class HistorialMedicoService {

    

    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;

    @Autowired
    private MascotasClient mascotasClient;

    @Autowired
    private UsuarioClient usuarioClient;
    
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

    public List<HistorialMedico> obtenerTodosLosHistoriales() {
        return historialMedicoRepository.findAll();

    }

    public Optional<HistorialMedico> obtenerHistorialMedicoPorId(Long id) {
        return historialMedicoRepository.findById(id);

    }

    

   
   
   public boolean eliminarHistorialMedico(Long id) {
    if (historialMedicoRepository.existsById(id)) {
        historialMedicoRepository.deleteById(id);
        return true;
    }
    return false;
    }


    
}
