package com.example.Soporte.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "soporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreUsuario; 

    @Column(nullable = false)
    private String emailUsuario;  

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;      

    @Column(nullable = false)
    private LocalDateTime fechaCreacion; 

    private String estado;

    private String tipoSolicitud; 

}

