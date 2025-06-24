package com.example.creacionusuario.dto;

import com.example.creacionusuario.model.Rol;
import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String username;
    private String correo;
    private String nombres;
    private String apellido;
    private Rol rol;
}
