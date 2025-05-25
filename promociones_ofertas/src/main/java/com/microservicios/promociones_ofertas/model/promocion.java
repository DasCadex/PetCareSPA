package com.microservicios.promociones_ofertas.model;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idpromocion;

    private String titulo;
    private String descripcion;

    


}
