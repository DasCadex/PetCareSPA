package com.microservicios.mascotas.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table ( name="mascotas ")
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Mascotas {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long mascotasId;

    @Column(nullable = false )
    private Long usuarioId;

   
    
    
    @Column (nullable = false, length = 30)
     private String nombre;

    @Column (nullable =  false, length = 2)
    private int edad;
    
    @Column (nullable = false , length = 1000)
    private String raza;


    @Column (nullable = false , length = 30)
    private String especie;

    @Column(nullable = false )
    private String nombredueno;

}
