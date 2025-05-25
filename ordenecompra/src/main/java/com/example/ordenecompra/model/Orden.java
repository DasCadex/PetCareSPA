package com.example.ordenecompra.model;

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
@Table(name= "ordenes")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_orden;


    @Column(nullable = false )
    private Long usuarioId;

 

    @Column(nullable = false )
    private Long productoId;

    @Column(nullable = false)
    private String nombreProducto;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable= false )
    private Double precioProducto;

    @Column(nullable = false )
    private Integer cantidad;

    @Column(nullable = true )
    private Long promocionid;

    @Column(nullable = true )
    private String promocionif;







    

}
