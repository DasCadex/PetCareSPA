package com.example.notificaciones.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // Cita Proxima , Pago Pendiente o Producto Agotado

    @Column(nullable = false)
    private String destinatario; //email@ejemplo.com, ID usuario 10

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje; 

    @Column(nullable = false)
    private LocalDateTime fechaCreacion; // Cuando se genero la notificación en nuestro sistema

    private LocalDateTime fechaEnvio; // Cuando se envio 

    private String estadoEnvio; // PENDIENTE, ENVIADA, FALLIDA, NO APLICA

    private String referenciaExternaId; // ID del elemento (cita id, pago id, producto id)

    
  
}