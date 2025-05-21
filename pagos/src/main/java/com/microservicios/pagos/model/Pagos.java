package com.microservicios.pagos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.GenerationType;

@Entity
@Data
@Table (name = "Pagos ")
@NoArgsConstructor
@AllArgsConstructor

public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    
    private long idpagos;
    @Column(nullable = false)
    private int monto;
    @Column(nullable = true)
    private String Descripcion;



    



}
