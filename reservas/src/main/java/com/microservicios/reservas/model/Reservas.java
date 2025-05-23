package com.microservicios.reservas.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name="reservas")
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Reservas {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservaId;

    @Column(nullable = false )
    private String fecha;

    @Column(nullable = false )
    private String motivo; // ej: "Consulta general", "Vacunación", etc.
    
    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = true )
    private String observaciones; // opcional

    @Column(nullable = false )

    private Double precio;

    @Column(nullable = false )
    private String nombrecliente;
}
