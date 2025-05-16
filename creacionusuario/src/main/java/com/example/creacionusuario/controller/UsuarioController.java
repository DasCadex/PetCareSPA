package com.example.creacionusuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.creacionusuario.dto.UsuarioDTO;
import com.example.creacionusuario.model.Rol;
import com.example.creacionusuario.model.Usuario;
import com.example.creacionusuario.service.RoleService;
import com.example.creacionusuario.service.UsuarioService;

@RestController
@RequestMapping("/api/v1")

public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;



    @GetMapping("/user")

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }


    @GetMapping("/roles")
    public ResponseEntity <List <Rol> > obtenerRoles(){
        List <Rol>  roles=roleService.bucarRol();

        if(roles.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }


    @PostMapping("/users")

    public ResponseEntity <?> crearUsuarios (@RequestParam String Username, String password, String nombres, String apellido, String correo,@RequestParam Long roleId){

        try{
            Usuario usuario1= usuarioService.crearUsuario(Username, password, nombres, apellido, correo, roleId);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario1);
        }catch(Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")

    public ResponseEntity <?> eliminarUsuario(@PathVariable Long id ){
        try{
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado con exito");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    




}
