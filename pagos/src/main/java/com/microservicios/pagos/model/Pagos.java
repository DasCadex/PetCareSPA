package com.microservicios.pagos.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID único del Pago ", example = "1")

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pagoId;

    @Schema(description = "Monto del Pago", example = "10.21")

    @Column(nullable = false)
    private Double monto;

    @Schema(description = "Descripcion del Pago", example = "Pago Factura")
    @Column(nullable = true)
    private String descripcion;

    @Schema(description = "Nombre del Cliente del pago", example = "Esteban Quito")
    @Column(nullable = false )
    private String nombrecliente;

    @Schema(description = "ID unico del orden de Compra", example = "3")
    @Column(nullable = false )
    private Long ordenCompraId;

   



    



}
