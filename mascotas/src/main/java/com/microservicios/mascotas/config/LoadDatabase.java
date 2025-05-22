package com.microservicios.mascotas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicios.mascotas.model.Mascotas;
import com.microservicios.mascotas.repository.MascotasRepository;

@Configuration// 

public class LoadDatabase {

    @Bean
CommandLineRunner initDatabesa(MascotasRepository mascotasRepository){
    return args -> {
        if (mascotasRepository.count() == 0){

            Mascotas mascota1 = new Mascotas();
            mascota1.setNombre("pepe");
            mascota1.setEdad(5);
            mascota1.setRaza("poodle");
            mascota1.setEspecie("Canina");
            mascota1.setUsuarioId(1L); // asigna un usuario válido
            mascota1.setNombredueno("Juan Pérez");

            Mascotas mascota2 = new Mascotas();
            mascota2.setNombre("huevitorey");
            mascota2.setEdad(7);
            mascota2.setRaza("Pastor aleman");
            mascota2.setEspecie("Canina");
            mascota2.setUsuarioId(1L);
            mascota2.setNombredueno("Juan Pérez");

            Mascotas mascota3 = new Mascotas();
            mascota3.setNombre("PapiMiky");
            mascota3.setEdad(2);
            mascota3.setRaza("Cyclura lewisi");
            mascota3.setEspecie("Reptiles");
            mascota3.setUsuarioId(1L);
            mascota3.setNombredueno("Juan Pérez");

            mascotasRepository.save(mascota1);
            mascotasRepository.save(mascota2);
            mascotasRepository.save(mascota3);

            System.out.println("Datos iniciales cargados en la base de datos.");

        } else {
            System.out.println("¡Ya existen mascotas en la base de datos. No se insertaron nuevos.");
        }
    };
}



    
}
