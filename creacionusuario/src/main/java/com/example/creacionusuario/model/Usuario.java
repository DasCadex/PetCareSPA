package com.example.creacionusuario.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false , unique = true)
    private String correo;
    
    @Column(nullable = false )
    private String nombres;
    
    @Column(nullable = false)
    private String apellido;


    @ManyToOne
    @JoinColumn(name = "rol_id",nullable = false)
    @JsonIgnoreProperties("usuarios")
    private Rol rol;

}
