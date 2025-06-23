package com.microservicios.mascotas.model;

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
@Table ( name="mascotas ")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa los datos a ingrser para una mascota")

@Entity
public class Mascotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del historial médico", example = "1")
    private long mascotasId;

    @Column(nullable = false )
    @Schema(description = "ID del usuario (dueño de la mascota)", example = "101")
    private Long usuarioId;

    @Column (nullable = false, length = 30)
    @Schema(description = "nombre de la mascota", example = "DiosColoColoBorrachoRojas")
    private String nombre;

    @Column (nullable =  false, length = 2)
    @Schema(description = "edad de la mascota", example = "200 años")
    private int edad;
    
    @Column (nullable = false , length = 1000)
    @Schema(description = "Tipo de raza", example = "Pastor Aleman")
    private String raza;

    @Column (nullable = false , length = 30)
    @Schema(description = "Tipo de espicie", example = "Canina - felina - etc")
    private String especie;

    @Column(nullable = false )
    @Schema(description = "nombre del amo", example = "Kratos Sepulveda")
    private String nombredueno;

    

}
