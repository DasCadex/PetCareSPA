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


@Entity
@Table(name = "soporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSoporte;

    @Column(nullable = false)
    private String nombreusuario; 

    @Column(nullable = false)
    private String emailusuario;  

    @Column(nullable = false)
    private String mensaje;      

    @Column(nullable = false)
    private String  fechaCreacion; 

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String tipoSolicitud; 
    
    
    @Column(nullable = false )
    private Long usuarioId;

}

