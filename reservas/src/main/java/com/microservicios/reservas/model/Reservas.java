package com.microservicios.reservas.model;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa una reserva realizada por un cliente")

public class Reservas {

    
    @Schema(description = "ID único de la reserva", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservaId;

     @Schema(description = "fijar la fecha de la reserva ", example = "2025-06-30")
    @Column(nullable = false )
    private String fecha;

    @Schema(description = "se pregunta el motio de la visita ", example = "Consulta médica")
    @Column(nullable = false )
    private String motivo; // ej: "Consulta general", "Vacunación", etc.
    
    @Schema(description = "nos permite filtrar el usuario que agenada la hora ", example = "1")
    @Column(nullable = false)
    private Long usuarioId;

    @Schema(description = "Observaciones adicionales", example = "Paciente nuevo")
    @Column(nullable = true )
    private String observaciones; // opcional

    @Column(nullable = false )

    private Double precio;

    @Column(nullable = false )
    private String nombrecliente;
}
