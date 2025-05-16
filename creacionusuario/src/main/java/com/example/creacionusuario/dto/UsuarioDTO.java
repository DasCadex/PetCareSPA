package com.example.creacionusuario.dto;

import com.example.creacionusuario.model.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String correo;
    private String nombres;
    private String apellido;
    private Rol rol;
}