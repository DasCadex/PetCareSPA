package com.example.ordenecompra.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID único de la orden de compra ", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_orden;

    @Schema(description = "id del usuario que hace la compra ", example = "2")
    @Column(nullable = false )
    private Long usuarioId;

 
    @Schema(description = "id de los prodctos que se compraran ", example = "3")
    @Column(nullable = false )
    private Long productoId;

    @Schema(description = "nombre del producto que se compran ", example = "correa de perro")
    @Column(nullable = false)
    private String nombreProducto;

    @Schema(description = "nombre del usuario que compra", example = "cade")
    @Column(nullable = false)
    private String nombres;

    @Schema(description = "precio de los productos que se llevan ", example = "2500")
    @Column(nullable= false )
    private Double precioProducto;
    @Schema(description = "cantidad del poducto que se llevbara ", example = "30")
    @Column(nullable = false )
    private Integer cantidad;

    @Schema(description = "la promocion que se usara ", example = "2")
    @Column(nullable = true )
    private Long promocionid;

    @Column(nullable = true )
    private String promocionif;


}
