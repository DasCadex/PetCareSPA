package com.example.Rating.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;


@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idopinion;

    @Column(nullable = false)
    private Long idusuario;

    @Column(nullable = false)
    private Long idproducto;

    @Column(nullable = false)
    private Integer rating;


    @Column(nullable = false)
    private String nombreproducto;

    






}
