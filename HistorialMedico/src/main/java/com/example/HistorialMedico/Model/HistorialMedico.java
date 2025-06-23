package com.example.HistorialMedico.Model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "historialmedico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa un historial médico registrado para una mascota")

public class HistorialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del historial médico", example = "1")
    private Long idhistorial;

    @Column(nullable = false)
    @Schema(description = "ID de la mascota asociada", example = "15")
    private Long mascotaid;

    @Column(nullable = false)
    @Schema(description = "Fecha en que se realizó la consulta", example = "2025-06-21")
    private String fechaconsulta;

    @Column(nullable = false)
    @Schema(description = "Diagnóstico realizado por el veterinario", example = "Epatitis B")
    private String diagnostico;

    @Column(nullable = false)
    @Schema(description = "Tratamiento recomendado o aplicado", example = "Antibióticos por 7 días")
    private String tratamiento;

    @Column(nullable = false)
    @Schema(description = "ID del usuario (dueño de la mascota)", example = "101")
    private Long usuarioid;


    @Column(nullable = false)
    @Schema(description = "Nombre del veterinario que realizó la consulta", example = "Dra. Camila Torres")
    private String nombreveterinario;

    @Column(nullable = false)
    @Schema(description = "Nombre de la mascota atendida", example = "Luna")
    private String nombremascota;

    

}
