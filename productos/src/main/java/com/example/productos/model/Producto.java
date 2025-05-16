package com.example.productos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="productos ")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length = 12 )
    private int stock;

    @Column(nullable=false, length = 55)
    @NotBlank(message = "El Nombre del producto es obligatorio ")
    private String nombre_producto;

    @NotBlank(message = "la marca del producto es obligatorio")
    @Column(nullable=false, length = 55 )
    private String marca_producto; 

    
    @Column(nullable=false, length =  100000)
    private int precio_producto;




}
