package com.example.Soporte.Model;


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


@Entity
@Table(name = "soporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa un reclamo, solicitud o ayuda al usuario")

public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la solicitud", example = "1")
    private Long idSoporte;

    @Column(nullable = false)
    @Schema(description = "Nombre del cliente", example = "Alexis Sanchez")
    private String nombreusuario; 

    @Column(nullable = false)
    @Schema(description = "Email del cliente", example = "fe.ariragadag@gmail.com")
    private String emailusuario;  

    @Column(nullable = false)
    @Schema(description = "El mensaje que le quiera dejar ", example = "Se me perdio mi perro en la veterianaria que hacer??")
    private String mensaje;      

    @Column(nullable = false)
    @Schema(description = "fecha de posteo de la solicitud", example = "01/01/2025")
    private String  fechaCreacion; 

    @Column(nullable = false)
    @Schema(description = "EL estado de la solicitud", example = "pendiente")
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Tipo de solicitud del cliente", example = "Reclamo")
    private String tipoSolicitud; 
    
    
    @Column(nullable = false )
    @Schema(description = "El id del cliente", example = "2")
    private Long usuarioId;

}

