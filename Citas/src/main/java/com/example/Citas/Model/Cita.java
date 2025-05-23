package com.example.Citas.Model;

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
@Table(name = "citas") 
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cita { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idcitas;

    @Column(nullable = false)

    private String fechacita;

    @Column(nullable = false)


    private 

    




   
}


   

    

    







