package com.microservicios.Reservas.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Reservas {


    @Id
    
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private LocalDate fecha;
    private LocalTime hora;
    private String motivo; // ej: "Consulta general", "Vacunación", etc.
    private String estado; // ej: "PENDIENTE", "CONFIRMADA", "CANCELADA", etc.
    private String observaciones; // opcional
}
