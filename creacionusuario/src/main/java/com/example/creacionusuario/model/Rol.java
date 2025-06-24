package com.example.creacionusuario.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa el rol o perfil de acceso de un usuario dentro del sistema")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del rol", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del rol (por ejemplo: ADMIN, USER)", example = "ADMIN")
    private String nombre;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de usuarios que poseen este rol (relación ignorada en la serialización)")
    private List<Usuario> usar;
}
