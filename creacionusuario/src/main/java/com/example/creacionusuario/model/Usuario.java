package com.example.creacionusuario.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a un usuario del sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre de usuario utilizado para iniciar sesión", example = "usuario123")
    private String username;

    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario (encriptada)", example = "password123")
    private String password;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico del usuario", example = "usuario@email.com")
    private String correo;

    @Column(nullable = false)
    @Schema(description = "Nombres del usuario", example = "Carlos Alberto")
    private String nombres;

    @Column(nullable = false)
    @Schema(description = "Apellido del usuario", example = "González")
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    @JsonIgnoreProperties("usuarios")
    @Schema(description = "Rol que tiene asignado el usuario")
    private Rol rol;
}
