package com.microservicios.mascotas.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Table ( name="mascotas ")
@NoArgsConstructor

@Entity
public class Mascotas {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;
    
    
    @Column (nullable = true, length = 30)
    
    private String nombre;
    @Column (nullable =  true , length = 2)
    
    private int edad;
    
    @Column (nullable = true , length = 10)
    
    private String raza;
    @Column (nullable = true , length = 30)
    
    private String especie;

}
