package com.example.creacionusuario.model;

import java.util.Date;

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
@Table(name = "Usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Usuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    

    private Integer idUsuario;

    @Column(nullable = true)
    private String correo;

    @Column(nullable = false )
    private String nombres; 

    @Column(nullable = false )
    private Date fechaNaciento;

    @Column(nullable = false )
    private String apellido;


    @Column(nullable = false )
    private String contrasena;



    @Column(unique = true, nullable = false, length = 12)
    private String rut;


    @Column(unique = true, nullable = false, length = 15)
    private String numeroTelefono;

    

}
