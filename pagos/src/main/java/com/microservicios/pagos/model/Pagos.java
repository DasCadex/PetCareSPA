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
@Table (name = "Pagos")
@NoArgsConstructor
@AllArgsConstructor

public class Pagos {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pagoId;

    @Column(nullable = false)
    private Double monto;


    @Column(nullable = true)
    private String descripcion;

    @Column(nullable = false )
    private String nombrecliente;

    @Column(nullable = false )
    private Long ordenCompraId;

   



    



}
