package com.example.HistorialMedico.Service;

import com.example.HistorialMedico.Model.HistorialMedico;
import com.example.HistorialMedico.Repository.HistorialMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class HistorialMedicoService {

    private final HistorialMedicoRepository historialMedicoRepository;

    @Autowired
    public HistorialMedicoService(HistorialMedicoRepository historialMedicoRepository) {
        this.historialMedicoRepository = historialMedicoRepository;
    }

    public HistorialMedico crearHistorialMedico(HistorialMedico historial) {

        if (historial.getMascotaid() == null) {
            throw new IllegalArgumentException("El ID de la mascota es obligatorio");
        }
        if (historial.getFechaconsulta() == null || historial.getFechaconsulta().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de consulta es obligatoria.");
        }
        if (historial.getDiagnostico() == null || historial.getDiagnostico().trim().isEmpty()) {
            throw new IllegalArgumentException("El diagnostico es obligatorio");
        }
        return historialMedicoRepository.save(historial);

    }

    public List<HistorialMedico> obtenerTodosLosHistoriales() {
        return historialMedicoRepository.findAll();

    }

    public Optional<HistorialMedico> obtenerHistorialMedicoPorId(Long id) {
        return historialMedicoRepository.findById(id);

    }

    public List<HistorialMedico> obtenerHistorialesPorMascotaId(Long mascotaId) { 
        return historialMedicoRepository.findByMascotaid(mascotaId);
    }

    public Optional<HistorialMedico> actualizarHistorialMedico(Long id, HistorialMedico historialActualizado) {

        return historialMedicoRepository.findById(id).map(historialExistente -> {

            if (historialActualizado.getFechaconsulta() != null && !historialActualizado.getFechaconsulta().trim().isEmpty()) {
                historialExistente.setFechaconsulta(historialActualizado.getFechaconsulta());
            }
            
            if (historialActualizado.getDiagnostico() != null && !historialActualizado.getDiagnostico().trim().isEmpty()) {
                historialExistente.setDiagnostico(historialActualizado.getDiagnostico());
            }

            if (historialActualizado.getTratamiento() != null) {
                historialExistente.setTratamiento(historialActualizado.getTratamiento());
            }
            return historialMedicoRepository.save(historialExistente);

            
        });
   } 
   
   public boolean eliminarHistorialMedico(Long id) {
    if (historialMedicoRepository.existsById(id)) {
        historialMedicoRepository.deleteById(id);
        return true;
    }
    return false;
    }


    
}
