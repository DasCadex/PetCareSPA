package com.microservicios.mascotas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.repository.MascotasRepository;

@Configuration// 

public class LoadDatabase {

    @Bean // permite la definicion  y gestion de los objetos que componene la aplicacion 
    CommandLineRunner initDatabesa(MascotasRepository mascotasRepository){

        return args -> {
            if (mascotasRepository.count() == 0){

                Mascotas mascota1 = new Mascotas(0, "pepe", 5, "poodle", "Canina");
                Mascotas mascota2 = new Mascotas(0, "huevitorey", 7, "Pastor aleman", "canina");
                Mascotas mascota3 = new Mascotas(0, "PapiMiky", 2, "Cyclura lewisi", "Reptiles");


                mascotasRepository.save(mascota1);
                mascotasRepository.save(mascota2);
                mascotasRepository.save(mascota3);
                System.out.println("Datos iniciales cargados en la base de datos.");

            }else{
                System.out.println("!Ya existen mascotas en la base de datos. No se insertaron nuevos. ");
            }

        };

    }

    
}
