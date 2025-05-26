package com.example.Notificaciones.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Notificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idnotificacion;

    @Column(nullable = false)
    private Long idpago; 

    @Column(nullable = false, length = 500)
    private String mensaje; 

    @Column(nullable = false)
    private String fechaCreacion; 

    @Column(nullable = false)
    private boolean leida = false; 

}
