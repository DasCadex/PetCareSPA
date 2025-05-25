package com.example.HistorialMedico.Repository;

import com.example.HistorialMedico.Model.HistorialMedico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long>{
    
}
