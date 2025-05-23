package com.example.HistorialMedico.Repository;

import com.example.HistorialMedicoMascotas.Model.HistorialMedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedicoModel, Long> {
    
}
    