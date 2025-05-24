package com.example.creacionusuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.creacionusuario.model.Rol;
import com.example.creacionusuario.model.Usuario;
import com.example.creacionusuario.repository.RoleRepositoy;
import com.example.creacionusuario.repository.UsuarioRepository;

@Configuration

public class LoadDatabase {

    @Bean
    CommandLineRunner iniDatabase(RoleRepositoy roleRepo, UsuarioRepository userRepo){//de esta manera ara lo que le digamos apenas corra la base de datos 

        return arg -> {

            if(roleRepo.count()==0 && userRepo.count()==0){

                Rol admin = new Rol();
                    admin.setNombre("Administrador ");
                    roleRepo.save(admin);

                    Rol user= new Rol();
                    user.setNombre("Cliente");
                    roleRepo.save(user);


                    Rol doc= new Rol();
                    doc.setNombre("Veterinario");
                    roleRepo.save(doc);

                    Rol gestor=new Rol();
                    gestor.setNombre("gestor de inventario ");
                    roleRepo.save(gestor);


                    Rol soporte=new Rol();
                    soporte.setNombre("Soporte y administrador de sistemas");
                    roleRepo.save(soporte);

                    userRepo.save(new Usuario(null,"cadex","123","fege@123","fernando","villalobo",admin));
                    userRepo.save(new Usuario(null,"gabrielex","321","dasCadez@123","gabriel","jorquera",user));
                    userRepo.save(new Usuario(null,"gestevan","567","pera@123","estevan","torres",doc));
                    userRepo.save(new Usuario(null,"DasCadex","5676","pera777@123","fortachon","gaspar",gestor));
                    userRepo.save(new Usuario(null,"nicolas123","5676","pera777@12356","nicolas","sotomayor",soporte));

                    System.out.println("Datos iniciales cargados ");







            }
            else{
                System.out.println("Datos ya existen. no realizo carga");

            }
        };

    }

}
