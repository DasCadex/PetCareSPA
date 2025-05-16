package com.microservicios.mascotas.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Mascotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;
    
    private String nombre;
    private int edad;
    private String raza;
    private String especie;

}
