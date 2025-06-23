package com.microservicios.promociones_ofertas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name="Promociones")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class promocion {

    @Schema(description = "ID unico de la orden de una promocion ", example = "1")

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idpromocion;

    @Schema(description = "Titulo de la Promocion ", example = " 10% de Descuento en Comida para gatos")
    private String titulo;
    @Schema(description = "Descripcion de la promocion ", example = "Descuento en marcas como catshow y mastercat")
    private String descripcion;

    


}
