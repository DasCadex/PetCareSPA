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

    @Column(nullable = false)
    private String diagnostico;

    @Column(nullable = false)
    private String tratamiento;

    @Column(nullable = false)
    private Long usuarioid;


    @Column(nullable = false)
    private String nombreveterinario;

    @Column(nullable = false)
    private String nombremascota;

    

}
