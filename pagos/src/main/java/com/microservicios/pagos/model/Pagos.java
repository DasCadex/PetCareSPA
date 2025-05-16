package com.microservicios.pagos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.GenerationType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    


}
