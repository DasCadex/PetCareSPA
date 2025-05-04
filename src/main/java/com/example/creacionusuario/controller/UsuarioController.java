package com.example.creacionusuario.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.creacionusuario.model.Usuario;
import com.example.creacionusuario.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")

public class UsuarioController {
    

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping

    public ResponseEntity <List<Usuario>> listaUsuario(){
         List<Usuario> list2 = usuarioService.buscarUsuario();

         if(list2.isEmpty()){
            return ResponseEntity.noContent().build();

         }
         return ResponseEntity.ok(list2);
    }




    @PostMapping
    public ResponseEntity <Usuario> guardarUsuario (@RequestBody Usuario usuario){

        Usuario us = usuarioService.agregarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(us);
    } 

    @GetMapping("/{id}")
    public ResponseEntity <Usuario> buscarUsuarioPorId (@PathVariable Integer id){

        try{
            Usuario usuario=usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);

        }catch(Exception e){

            return ResponseEntity.notFound().build();
        }

    }


    @DeleteMapping("/{id}")


    public ResponseEntity <?> borrarUsuarioPorId (@PathVariable Long id){

        try {
            Usuario user= usuarioService.buscarPorId(id);
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping({"/id"})
    public ResponseEntity <Usuario>  actualizarPacientePorId (@PathVariable Integer id, @RequestBody Usuario user){


        try{

            Usuario usuario2= usuarioService.buscarPorId(id);

            usuario2.setIdUsuario(id);
            usuario2.setRut(user.getRut());
            usuario2.setNombres(user.getNombres());
            usuario2.setApellido(user.getApellido());
            usuario2.setNumeroTelefono(user.getNumeroTelefono());
            usuario2.setFechaNaciento(user.getFechaNaciento());
            return ResponseEntity.ok(usuario2);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }

    }





    

}
