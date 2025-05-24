package com.example.HistorialMedico.Model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "historialmedico")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistorialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idhistorial;

    @Column(nullable = false)
    private Long mascotaid;

    @Column(nullable = false)
    private String fechaconsulta;

    @Column(nullable = false, length = 255)
    private String diagnostico;

    @Lob
    private String tratamiento;

    

}
