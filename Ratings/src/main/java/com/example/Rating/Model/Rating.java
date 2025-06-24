package com.example.Rating.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una calificación (rating) hecha por un usuario sobre un producto")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la calificación", example = "1")
    private Long idopinion;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que realiza la calificación", example = "5")
    private Long idusuario;

    @Column(nullable = false)
    @Schema(description = "ID del producto calificado", example = "7")
    private Long idproducto;

    @Column(nullable = false)
    @Schema(description = "Valor del rating (de 1 a 5)", example = "4")
    private Integer rating;

    @Column(nullable = false)
    @Schema(description = "Descripción del producto calificado", example = "Comedero automático para gatos")
    private String productoDes;
}
